package com.bluedot.framework.simplemybatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @version: 1.0
 * @className: DefaultParameterHandler
 * @Description:
 * @Author: KangLongPing
 * @Date: 2021/8/21 10:11
 */
public class DefaultParameterHandler implements ParameterHandler {

	// 传入对数
	private final Object parameter;

	public DefaultParameterHandler(Object parameter) {
		this.parameter = parameter;
	}

	/**
	 * 设置参数到预处理对象中
	 *
	 * @param paramPreparedStatement 预处理对象
	 */
	@Override
	public void setParameter(PreparedStatement paramPreparedStatement) {
		try {
			if (null != parameter) {
				if (parameter.getClass().isArray()) {
					Object[] params = (Object[]) parameter;
					for (int i = 0; i < params.length; i++) {
						paramPreparedStatement.setObject(i + 1, params[i]);
					}
				} else {
					paramPreparedStatement.setObject(1, parameter);
				}
			}
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}
}
