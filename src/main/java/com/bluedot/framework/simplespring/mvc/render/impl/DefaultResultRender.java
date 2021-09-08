package com.bluedot.framework.simplespring.mvc.render.impl;


import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.render.ResultRender;

import java.io.IOException;

/**
 *  默认渲染器
 * @author xxbb
 */
public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws IOException {
        if (requestProcessorChain.getRequestPath().endsWith("/")) {
             requestProcessorChain.getResp().sendRedirect("index.jsp");
        }
        requestProcessorChain.getResp().setStatus(requestProcessorChain.getResponseCode());
    }
}
