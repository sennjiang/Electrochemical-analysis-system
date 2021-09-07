package com.bluedot.framework.simplespring.mvc.processor.impl;

import com.bluedot.electrochemistry.service.SearchService;
import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
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
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
//        HttpServletRequest request = requestProcessorChain.getReq();
//        Map<String, String[]> parameterMap = request.getParameterMap();
        HttpServletResponse response = requestProcessorChain.getResp();
        SearchService searchService = new SearchService();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("service","list");
        new SearchService().doService(hashMap);
        List list = (List) hashMap.get("list");
        String json = JsonUtil.getJson(list);
        response.getWriter().write(json);
        return false;
    }
}
