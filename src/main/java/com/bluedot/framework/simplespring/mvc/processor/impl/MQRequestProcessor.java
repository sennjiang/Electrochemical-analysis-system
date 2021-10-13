package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.electrochemistry.pojo.domain.Operation;
import com.bluedot.electrochemistry.service.OperationService;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.mapper.CommonMapper;
import com.bluedot.framework.simplespring.mvc.monitor.BlockQueue;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.mvc.render.impl.JsonResultRender;
import com.bluedot.framework.simplespring.util.LogUtil;

import javafx.util.Pair;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/7-16:35
 */
public class MQRequestProcessor implements RequestProcessor {

    private Logger logger = LogUtil.getLogger();

    /**
     * test baseBoundary
     */
    private String baseBoundary;

    private static List<String> writeList = new ArrayList<>();

    /**
     * 获取到当前运行环境的可用处理器数量
     */
    private final static int POLLER_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池对象 核心线程2 一个 monitor 一个预留线程
     * 最大线程数 POLLER_THREAD_COUNT * 8 按照机器而定
     * 空闲线程存活时间2s 可视为最大响应时间
     * 数组容量 64 可视为一时间最大请求，再来请求进行阻塞 待处理
     */
    private ExecutorService executors = new ThreadPoolExecutor(2,
            POLLER_THREAD_COUNT * 8,
            2, TimeUnit.SECONDS,
            new BlockQueue<Runnable>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 标识request 唯一id 并线程安全，自增
     */
    private AtomicLong requestId = new AtomicLong(1);

    /**
     * 初始化白名单
     */
    static {
        writeList.add("0101");
    }

    /**
     * 日志线程处理对象
     */
    class LogTask implements Runnable {
        private Data data;

        public LogTask(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            Operation operation = packageOperation();
            OperationService operationService = (OperationService) BeanContainer.getInstance().getBean(OperationService.class);
            operationService.insertOperation(operation);
        }

        /**
         * 包装 Operation 对象
         * @return Operation
         */
        private Operation packageOperation() {
            Class<?> service = data.getService();
            String name = service.getName();
            String serviceMethod = data.getServiceMethod();
            Operation operation = new Operation();

            if (data.containsKey("message")) {
                operation.setMessage((String) data.get("message"));
            }

            operation.setLevel(data.containsKey("error") ? "error" : "info");

            operation.setUser(((int)data.get("user")));

            operation.setRecorder(name + "." + serviceMethod);

            String boundary = (String) data.get("boundary");
            operation.setType(data.containsKey(boundary) ? CommonMapper.typeMapper.get(boundary) : (short) 1);

            operation.setTime(new Timestamp(System.currentTimeMillis()));

            operation.setBoundary(boundary);

            if ("FileService".equals(name)) {
                operation.setFile(true);
                operation.setFileType(CommonMapper.fileTypeMapper.get(boundary));
            }
            return operation;
        }
    }

    /**
     * 处理器线程
     * 监听向上队列 取得数据返回
     */
    class Adapter implements Callable<Data> {

        BeanContainer beanContainer = BeanContainer.getInstance();

        private Data newData;

        public Adapter(Data data) {
            this.newData = data;
        }

        @Override
        public Data call() throws Exception {

            logger.info("开始处理请求--- 请求路径: {}", newData.getRequest().getPathInfo());
            Class clazz = newData.getService();

            BaseService service = (BaseService) beanContainer.getBean(clazz);
            //System.out.println(newData!=null?true:false);
            service.doService(newData);
            logger.info("处理请求结束--- 请求id: {}", newData.get("requestId"));
            return newData;
        }
    }


    public MQRequestProcessor(Properties config) {
        baseBoundary = config.getProperty("boundary");
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        Data data = doRequest(requestProcessorChain);

        if (data.get("username") == null && !writeList.contains(data.get("boundary"))) {
            return false;
        }

        Adapter adapter = new Adapter(data);

        Future<Data> submit = executors.submit(adapter);

        Data newData = submit.get();

        //暂不开启
//        LogTask task = new LogTask(newData);
//
//        executors.submit(task);

        requestProcessorChain.setResultRender(new JsonResultRender(newData));

        return false;
    }

    /**
     * 对request进行处理封装
     *
     * @param requestProcessorChain 责任链
     * @return Data
     */
    private Data doRequest(RequestProcessorChain requestProcessorChain) throws Exception {

        HttpServletRequest request = requestProcessorChain.getReq();

        String header = request.getHeader("Content-Type");
        String username = request.getHeader("Authorization");
        Data data = new Data(request);
        data.put("username",username);
        if (header == null || header.startsWith("application")){

            logger.info("parameterMap ---> data : {}",data);
            String boundary = (String) data.get("boundary");
            if (baseBoundary != null && boundary == null) {
                boundary = baseBoundary;
                data.put("boundary", boundary);
            }

            if ("".equals(boundary) || boundary == null) {
                try {
                    requestProcessorChain.getResp().sendRedirect("index.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            data.setRequest(request);
            data.put("boundary",boundary);
        } else {
            logger.debug("start parse file request ... ");
            String realPath = request.getSession().getServletContext().getRealPath("/uploads");
            java.io.File file = new java.io.File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String value = item.getString();
                    String name = item.getFieldName();
                    data.put(name,value);
                } else {
                    String filename = item.getName();
                    File file1 = new File(realPath, filename);
                    data.put("file",file1);
                    item.write(file1);
                    item.delete();
                }
            }
        }
        Pair<Class, String> classStringPair = CommonMapper.methodMapper.get(data.get("boundary"));

        data.put("service", classStringPair.getKey());
        data.put("serviceMethod", classStringPair.getValue());
        data.put("requestId", requestId.getAndIncrement());

        return data;
    }
}


