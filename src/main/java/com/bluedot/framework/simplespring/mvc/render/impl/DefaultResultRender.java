package com.bluedot.framework.simplespring.mvc.render.impl;


import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.render.ResultRender;

/**
 *  默认渲染器
 * @author xxbb
 */
public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain){
        requestProcessorChain.getResp().setStatus(requestProcessorChain.getResponseCode());
    }
}
