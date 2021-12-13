package com.bluedot.electrochemistry.service;

/**
 * @author Sens
 */
public interface Lifecycle {
    /**
     * 初始化
     */
    void init();

    /**
     * 销毁
     */
    void destroy();
}
