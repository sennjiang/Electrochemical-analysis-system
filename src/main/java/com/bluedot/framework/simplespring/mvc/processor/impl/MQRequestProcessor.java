package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.electrochemistry.service.SearchService;
import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.monitor.BlockQueue;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.bluedot.framework.simplespring.mvc.monitor.QueueMonitor;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/7-16:35
 */
public class MQRequestProcessor implements RequestProcessor {
    private BlockQueue blockQueue;

    private QueueMonitor queueMonitor;


    public MQRequestProcessor() {
        queueMonitor = new QueueMonitor(10);
        blockQueue = queueMonitor.getBlockQueue();
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if (requestProcessorChain.getRequestPath().endsWith("/")) {
            requestProcessorChain.getResp().sendRedirect("index.jsp");
            return false;
        }
        if (queueMonitor.getRunning() == false) {
            queueMonitor.setRunning(true);
            new Thread(queueMonitor).start();
        }

        HttpServletRequest request = requestProcessorChain.getReq();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Data data = new Data(parameterMap,requestProcessorChain.getResp());
        data.put("service","SearchService");
        data.put("serviceMethod","list");
        blockQueue.put(data);
        return false;
    }
}
