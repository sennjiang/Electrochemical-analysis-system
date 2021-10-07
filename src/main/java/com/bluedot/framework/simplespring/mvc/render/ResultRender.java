package com.bluedot.framework.simplespring.mvc.render;


import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;

/**
 * 渲染请求结果
 * @author 1
 */
public interface ResultRender {
    /**
     * 执行渲染
     * @param requestProcessorChain 请求责任链
     * @throws Exception 异常
     */
     void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
