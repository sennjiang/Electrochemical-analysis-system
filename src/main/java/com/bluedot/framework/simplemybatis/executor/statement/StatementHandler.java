package com.bluedot.framework.simplemybatis.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @version: 1.0
 * @className: StatementHandler
 * @Description:
 * @Author: KangLongPing
 * @Date: 2021/8/21 10:43
 */
public interface StatementHandler {
	/**
	 * SQL预处理
	 *
	 * @param connection 连接
	 * @return 完成预处理的对象
	 * @throws SQLException sql异常
	 */
	PreparedStatement prepared(Connection connection) throws SQLException;

	/**
	 * 查询数据库操作
	 *
	 * @param preparedStatement statement对象
	 * @return 查询结果集
	 * @throws SQLException sql异常
	 */
	ResultSet query(PreparedStatement preparedStatement) throws SQLException;

	/**
	 * 数据库的增删改操作
	 *
	 * @param preparedStatement statement对象
	 * @return 受影响的行数
	 * @throws SQLException sql异常
	 */
	int update(PreparedStatement preparedStatement) throws SQLException;
}
