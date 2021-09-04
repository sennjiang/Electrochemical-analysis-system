package com.bluedot.framework.simplemybatis.executor.resultSet;

import com.bluedot.framework.simplemybatis.mapping.MappedStatement;
import com.bluedot.framework.simplemybatis.utils.ReflectUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @version: 1.0
 * @className: DefaultResultSetHandler
 * @Description: 结果集处理
 * @Author: KangLongPing
 * @Date: 2021/8/21 10:18
 */
public class DefaultResultSetHandler implements ResultSetHandler {

	// 需要获取该对象的resultType信息
	private final MappedStatement mappedStatement;

	public DefaultResultSetHandler(MappedStatement mappedStatement) {
		this.mappedStatement = mappedStatement;
	}

	/**
	 * 对查询结果集进行封装，封装成对应类的List集合
	 *
	 * @param resultSet 结果集
	 * @param <E>       泛型
	 * @return 对应类的List集合
	 */
	@Override
	public <E> List<E> handleResultSet(ResultSet resultSet) {
		try {
			// 封装结果集对象
			List<E> result = new ArrayList<>();
			if (null == resultSet) {
				return null;
			}
			// 获取数据库元数据
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			// 处理结果集
			while (resultSet.next()) {
				// 如果查询是为了查询数据条数
				String rowObjectType = mappedStatement.getReturnType();
				// 由于基本数据类型的包装类无法反射实例化，这里要提前判断
				E rowObject;
				if (rowObjectType.endsWith("Long")) {
					rowObject = (E) resultSet.getObject(1);
				} else {
					// 实例化结果集对应的类
					rowObject = (E) Class.forName(rowObjectType).newInstance();
					// 遍历每一行数据并封装进对象中
					for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
						//获取列名和该列的值，从1开始，这里获取的是该列的别名
						//一般多表查询时意味着所查询的类也可能是由多个数据库对应类组合而成，那么
						//该类的属性名也将不会和数据库列名一一对应，故需要用别名去对应属性名
						String columnName = resultSetMetaData.getColumnLabel(i + 1);
						Object columnValue = resultSet.getObject(i + 1);

						// 数据库表中可能有空字段
						if (columnValue != null) {
							// 将值赋值给类对象
							ReflectUtils.invokeSet(rowObject, columnName, columnValue);
						}
					}
				}
				result.add(rowObject);
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
