package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.electrochemistry.service.SearchService;
import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.monitor.BlockQueue;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.bluedot.framework.simplespring.mvc.monitor.QueueMonitor;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.util.JsonUtil;
import com.bluedot.framework.simplespring.util.LogUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/7-16:35
 */
public class MQRequestProcessor implements RequestProcessor{

    class Adapter implements Runnable{
        RequestProcessorChain requestProcessorChain;

        public Adapter(RequestProcessorChain requestProcessorChain) {
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
            logger.info("数据为-----{}",data.getClass());
            data.setRequest(request);
            data.put("service","SearchService");
            data.put("serviceMethod","list");

            try {
                downBlockQueue.put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String threadName = Thread.currentThread().getName();

            Data newData = null;

            while (!hadFind) {
                try {
                    hadFind = upBlockQueue.hadOne(threadName);
                    if (hadFind) {
                        newData = upBlockQueue.take();
                        logger.debug("json----{}",JsonUtil.getJson(newData));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (hadFind && (newData != null)) {
                try {
                    requestProcessorChain.getResp().getWriter().write(JsonUtil.getJson(newData));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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




    public MQRequestProcessor() {
        queueMonitor = new QueueMonitor(10);
        downBlockQueue = queueMonitor.getDownBlockQueue();
        upBlockQueue = queueMonitor.getUpBlockQueue();
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        if (requestProcessorChain.getRequestPath().endsWith("/")) {
            return false;
        }

        Adapter adapter = new Adapter(requestProcessorChain);
        adapter.run();

        return false;
    }
}

