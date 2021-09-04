package com.bluedot.framework.simplemybatis.session;

import com.bluedot.framework.simplemybatis.session.defaults.DefaultSqlSessionFactory;
import com.bluedot.framework.simplemybatis.utils.BeanContainer;
import com.bluedot.framework.simplemybatis.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @className: SqlSessionFactoryBuilder
 * @version: 1.0
 * @Description:
 * @Author: KangLongPing
 * @Date: 2021/8/21 021 14:59
 */
public class SqlSessionFactoryBuilder {
	/**
	 * 读取配置文件构建SqlSessionFactory工厂
	 * 将配置文件的解析成输入流的工作也放到该方法中
	 *
	 * @param fileName 配置文件名
	 * @return 工厂对象
	 */
	public SqlSessionFactory build(String fileName) {
		InputStream is = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);
		return build(is);
	}

	public SqlSessionFactory build(InputStream inputStream) {
		try {
			Configuration.props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory factory = DefaultSqlSessionFactory.getInstance(new Configuration());
		// /将sqlFactory注入到IoC容器中
		// 注明: 此处由原来在simplespring包下的LogUtil改为了simplemybatis包下的LogUtils
		LogUtils.getLogger().debug("\"load bean: \"+factory.getClass().getName()");
		// todo 这里是simplespring中的bean容器
		BeanContainer.getInstance().addBean(factory.getClass(), factory);
		return factory;
	}
}
