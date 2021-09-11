package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.monitor.BlockQueue;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.bluedot.framework.simplespring.mvc.monitor.QueueMonitor;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.mvc.render.impl.DefaultResultRender;
import com.bluedot.framework.simplespring.mvc.render.impl.InternalErrorResultRender;
import com.bluedot.framework.simplespring.mvc.render.impl.JsonResultRender;
import com.bluedot.framework.simplespring.util.JsonUtil;
import com.bluedot.framework.simplespring.util.LogUtil;
import javafx.util.Pair;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/7-16:35
 */
public class MQRequestProcessor implements RequestProcessor{

    /**
     * service映射map
     */
    private Map<String, Pair<Class,String>> xmlMap;

    private Logger logger = LogUtil.getLogger();

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

    private String baseBoundary;


    /**
     * 处理器线程
     * 将前端的数据进行处理后 存入队列 并监听向上队列 取得数据返回
     */
    class Adapter implements Runnable{

        private Map<String, Pair<Class,String>> xmlMap;

        RequestProcessorChain requestProcessorChain;

        public Adapter(RequestProcessorChain requestProcessorChain, Map<String, Pair<Class, String>> xmlMap) {
            this.xmlMap = xmlMap;
            this.requestProcessorChain = requestProcessorChain;
        }

        @Override
        public void run() {
            Boolean hadFind = false;

            if (queueMonitor.getRunning() == false) {
                queueMonitor.setRunning(true);
                new Thread(queueMonitor).start();
            }

            HttpServletRequest request = requestProcessorChain.getReq();
            Map<String, String[]> parameterMap = request.getParameterMap();
            Data data = new Data(parameterMap,Thread.currentThread().getName());
            String boundary = (String) data.get("boundary");
            if (baseBoundary != null || boundary == null) {
                boundary = baseBoundary;
                data.put("boundary", boundary);
            }

            if (requestProcessorChain.getRequestPath().endsWith("/")) {
                requestProcessorChain.setResultRender(new DefaultResultRender());
            }

            if ("".equals(boundary) || boundary == null) {
                try {
                    requestProcessorChain.getResp().sendRedirect("index.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.info("数据为-----{}",data.getClass());
            data.setRequest(request);
            Pair<Class, String> classStringPair = xmlMap.get(boundary);

            data.put("service",classStringPair.getKey());
            data.put("serviceMethod",classStringPair.getValue());

            try {
                downBlockQueue.put(data);

                String threadName = Thread.currentThread().getName();

                Data newData = null;

                while (!hadFind) {
                        hadFind = upBlockQueue.hadOne(threadName);
                        if (hadFind) {
                            newData = upBlockQueue.take();
                            logger.debug("json --- data: {}",JsonUtil.toJson(newData));

                            //判断是否存在错误 若存在 则交给 异常处理器处理。
                            if (newData.containsKey("error")) {
                                Exception error = (Exception) newData.get("error");
                                requestProcessorChain.setResultRender(new InternalErrorResultRender(error.getMessage()));
                            }else {
                                requestProcessorChain.setResultRender(new JsonResultRender(newData));
                            }

                        }
                    Thread.sleep(25);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public MQRequestProcessor(Map<String, Pair<Class, String>> xmlMap, Properties config) {
        initQueueMonitor(config);
        this.xmlMap = xmlMap;
    }

    /**
     * 队列的初始化
     * @param config
     */
    private void initQueueMonitor(Properties config) {
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
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        if (requestProcessorChain.getRequestPath().endsWith("/")) {
            return false;
        }

        Adapter adapter = new Adapter(requestProcessorChain,xmlMap);
        adapter.run();

        return false;
    }
}

