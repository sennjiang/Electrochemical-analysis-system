package com.bluedot.framework.simplemybatis.executor.resultSet;

import java.sql.ResultSet;
import java.util.List;

/**
 * @version: 1.0
 * @Description: 结果集处理
 * @Author: KangLongPing
 * @Date: 2021/8/21 10:16
 */
public interface ResultSetHandler {
	/**
	 * 对查询结果集进行封装，封装成对应类的List集合
	 *
	 * @param resultSet
	 * @param <E>
	 * @return
	 */
	<E> List<E> handleResultSet(ResultSet resultSet);
}
