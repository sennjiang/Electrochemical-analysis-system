package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.monitor.BlockQueue;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.mvc.render.impl.InternalErrorResultRender;
import com.bluedot.framework.simplespring.mvc.render.impl.JsonResultRender;
import com.bluedot.framework.simplespring.util.LogUtil;

import javafx.util.Pair;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
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
     * service映射map
     */
    private Map<String, Pair<Class, String>> xmlMap;


    /**
     * test baseBoundary
     */
    private String baseBoundary;

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
     * 处理器线程
     * 监听向上队列 取得数据返回
     */
    class Adapter implements Callable<Data> {

        BeanContainer beanContainer = BeanContainer.getInstance();

        private Data newData;

        private Boolean hadFind = false;

        public Adapter(Data data) {
            this.newData = data;
        }

        public Boolean getHadFind() {
            return hadFind;
        }

        @Override
        public Data call() throws Exception {

            logger.info("开始处理请求--- 请求路径: {}", newData.getRequest().getPathInfo());
            Class clazz = newData.getService();

            BaseService service = (BaseService) beanContainer.getBean(clazz);
            service.doService(newData);
            logger.info("处理请求结束--- 请求id: {}", newData.get("requestId"));
            return newData;
        }
    }


    public MQRequestProcessor(Map<String, Pair<Class, String>> xmlMap, Properties config) {
        this.xmlMap = xmlMap;
        baseBoundary = config.getProperty("boundary");
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        Data data = doRequest(requestProcessorChain);

        Adapter adapter = new Adapter(data);

        Future<Data> submit = executors.submit(adapter);

        Data newData = submit.get();

        //TODO 日志处理 多线程处理 并发处理

        //判断是否异常
        if (newData.containsKey("error")) {
            Exception error = (Exception) newData.get("error");
            requestProcessorChain.setResultRender(new InternalErrorResultRender(error.getMessage()));
        }else {
            requestProcessorChain.setResultRender(new JsonResultRender(newData));
        }

        return false;
    }

    /**
     * 对request进行处理封装
     *
     * @param requestProcessorChain 责任链
     * @return Data
     */
    private Data doRequest(RequestProcessorChain requestProcessorChain) {

        HttpServletRequest request = requestProcessorChain.getReq();


        Data data = new Data(request);
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
        Pair<Class, String> classStringPair = xmlMap.get(boundary);

        data.put("service", classStringPair.getKey());
        data.put("serviceMethod", classStringPair.getValue());
        data.put("requestId", requestId.getAndIncrement());
        return data;
    }
}

