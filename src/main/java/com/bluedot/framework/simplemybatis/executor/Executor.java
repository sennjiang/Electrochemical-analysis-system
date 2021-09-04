package com.bluedot.framework.simplemybatis.executor;

import com.bluedot.framework.simplemybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @version: 1.0
 * @className: Executor
 * @Description: 直接进行数据库操作的接口
 * @Author: KangLongPing
 * @Date: 2021/8/21 10:20
 */
public interface Executor {
	/**
	 * 查询操作
	 *
	 * @param mappedStatement 泛型，和mappedStatement中的returnType有关
	 * @param parameter       sql信息对象
	 * @param parameter       参数
	 * @return 封装好的结果集
	 */
	<E> List<E> doQuery(MappedStatement mappedStatement, Object parameter);

	/**
	 * 更新操作，增删改
	 *
	 * @param mappedStatement sql信息对象
	 * @param parameter       参数
	 * @return 受影响的行数
	 */
	int doUpdate(MappedStatement mappedStatement, Object parameter);

	/**
	 * 设置自动提交，默认是false，不自动提交
	 *
	 * @param ifAutoCommit
	 */
	void setAutoCommit(boolean ifAutoCommit);

	/**
	 * 事务回滚
	 */
	void rollback();

	/**
	 * 提交事务
	 */
	void commit();

	/**
	 * 关闭连接
	 */
	void closeConnection();
}
