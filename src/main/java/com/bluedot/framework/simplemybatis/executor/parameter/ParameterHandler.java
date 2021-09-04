package com.bluedot.framework.simplemybatis.executor.parameter;

import java.sql.PreparedStatement;

/**
 * @version: 1.0
 * @Description: 处理传入参数
 * @Author: KangLongPing
 * @Date: 2021/8/21 10:08
 */
public interface ParameterHandler {
	/**
	 * 设置参数
	 *
	 * @param paramPreparedStatement 预处理对象
	 */
	void setParameter(PreparedStatement paramPreparedStatement);
}
