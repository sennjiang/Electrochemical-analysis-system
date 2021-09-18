package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.monitor.BlockQueue;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.bluedot.framework.simplespring.mvc.monitor.QueueMonitor;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.mvc.render.impl.DefaultResultRender;
import com.bluedot.framework.simplespring.mvc.render.impl.JsonResultRender;
import com.bluedot.framework.simplespring.util.JsonUtil;
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
     * 向下队列
     */
    private BlockQueue<Data> downBlockQueue;

    /**
     * 向上队列
     */
    private BlockQueue<Data> upBlockQueue;

    /**
     * 监听器
     */
    private QueueMonitor queueMonitor;


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
    private ExecutorService executors = new ThreadPoolExecutor(2, POLLER_THREAD_COUNT * 8, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(64));

    /**
     * 标识request 唯一id 并线程安全，自增
     */
    private AtomicLong requestId = new AtomicLong(1);


    /**
     * 处理器线程
     * 监听向上队列 取得数据返回
     */
    class Adapter implements Callable<Data> {

        private RequestProcessorChain requestProcessorChain;

        private Long requestId;

        private Boolean hadFind = false;

        public Adapter(RequestProcessorChain requestProcessorChain, Long requestId) {
            this.requestProcessorChain = requestProcessorChain;
            this.requestId = requestId;
        }

        public Boolean getHadFind() {
            return hadFind;
        }

        public void run() {

            if (queueMonitor.getRunning() == false) {
                logger.error("queueMonitor not running ...");
                throw new RuntimeException();
            }
            try {

                Data newData = null;
                logger.debug("Adapter ---> start listening   hadFind: {}   requestId {}", hadFind, requestId);
                while (!hadFind) {
                    hadFind = upBlockQueue.hadOne(requestId);
                    if (hadFind) {
                        newData = upBlockQueue.take();
                        logger.debug("请求结果 --- requestId: {}", newData.get("requestId"));
                        requestProcessorChain.setResultRender(new JsonResultRender(newData));
                    }
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Data call() throws Exception {
            if (queueMonitor.getRunning() == false) {
                logger.error("queueMonitor not running ...");
                throw new RuntimeException();
            }
            Data newData = null;
            try {
                logger.debug("Adapter ---> start listening   hadFind: {}   requestId {}", hadFind, requestId);
                while (!hadFind) {
                    hadFind = upBlockQueue.hadOne(requestId);
                    if (hadFind) {
                        newData = upBlockQueue.take();
                        logger.debug("请求结果 --- requestId: {}", newData.get("requestId"));
                        requestProcessorChain.setResultRender(new JsonResultRender(newData));
                    }
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return newData;
        }
    }


    public MQRequestProcessor(Map<String, Pair<Class, String>> xmlMap, Properties config) {
        QueueMonitor queueMonitor = initQueueMonitor(config);
        this.xmlMap = xmlMap;
        queueMonitor.setRunning(true);
        executors.execute(queueMonitor);
    }

    /**
     * 队列的初始化
     *
     * @param config
     */
    private QueueMonitor initQueueMonitor(Properties config) {
        try {
            this.baseBoundary = config.getProperty("boundary");
            String capacity = config.getProperty("monitor.queue.capacity");
            if ("".equals(capacity) || capacity == null) {
                queueMonitor = new QueueMonitor(10);
            } else {
                int num = Integer.parseInt(capacity);
                queueMonitor = new QueueMonitor(num);
            }
            queueMonitor.setOrderingThreshold(config.getProperty("monitor.thread.orderingThreshold"));
            queueMonitor.setFrequency(config.getProperty("monitor.thread.frequency"));
            queueMonitor.setSleepTime(config.getProperty("monitor.thread.sleepTime"));
        } catch (Exception e) {
            logger.error("init queueMonitor error");
        }
        logger.debug("init queueMonitor had complete ---> name: {} , " +
                        "capacity: {} , frequency: {} , sleepTime: {} ",
                queueMonitor.getClass().getName(),
                queueMonitor.getCapacity(),
                queueMonitor.getFrequency(),
                queueMonitor.getSleepTime());

        downBlockQueue = queueMonitor.getDownBlockQueue();
        upBlockQueue = queueMonitor.getUpBlockQueue();
        return queueMonitor;
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        Data data = doRequest(requestProcessorChain);

        downBlockQueue.put(data);

        Adapter adapter = new Adapter(requestProcessorChain, (Long) data.get("requestId"));
        //交给线程池处理
        FutureTask<Data> task = new FutureTask<>(adapter);

        Future<?> submit = executors.submit(task);

        submit.get();
        //TODO 日志处理 多线程处理 并发处理

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
        Map<String, String[]> parameterMap = request.getParameterMap();
        Data data = new Data(parameterMap, Thread.currentThread().getName());
        String boundary = (String) data.get("boundary");
        if (baseBoundary != null || boundary == null) {
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
        logger.info("数据为-----{}", data.getClass());
        data.setRequest(request);
        Pair<Class, String> classStringPair = xmlMap.get(boundary);

        data.put("service", classStringPair.getKey());
        data.put("serviceMethod", classStringPair.getValue());
        data.put("requestId", requestId.getAndIncrement());
        return data;
    }
}

