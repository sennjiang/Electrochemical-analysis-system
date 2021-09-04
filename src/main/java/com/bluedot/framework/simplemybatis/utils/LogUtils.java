package com.bluedot.framework.simplemybatis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version: v1.0
 * @className: LogUtils
 * @Description:
 * @Author: KangLongPing
 * @Date: 2021/8/20 15:10
 */
public class LogUtils {
	public static Logger getLogger() {
		return LoggerFactory.getLogger(Thread.currentThread().getName());
	}
}
