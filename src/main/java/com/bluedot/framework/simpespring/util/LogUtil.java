package com.bluedot.framework.simpespring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author JDsen99
 * @description LogUtil
 * @createDate 2021/8/27-14:43
 */
public class LogUtil {
    /**
     *  获取日志对象
     * @return Logger
     */
    public static Logger getLogger() {
        return LoggerFactory.getLogger(Thread.currentThread().getName());
    }
}
