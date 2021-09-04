package com.bluedot.framework.simplemybatis.binding;

import com.bluedot.framework.simplemybatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @version: v1.0
 * @className: MapperPoxyFactory
 * @Description: mapper代理工厂对象
 * @Author: KangLongPing
 * @Date: 2021/8/21 8:41
 */
public class MapperPoxyFactory<T> {
	// 被代理的接口
	private final Class<T> mapperInterface;

	/**
	 * 构造方法
	 *
	 * @param mapperInterface 需要被代理的接口类
	 */
	public MapperPoxyFactory(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	/**
	 * 创建mapper代理对象
	 *
	 * @param sqlSession 当前的sqlSession对象
	 * @return 代理实现类
	 */
	public T newInstance(SqlSession sqlSession) {
		MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, this.mapperInterface);
		return newInstance(mapperProxy);
	}

	public T newInstance(MapperProxy<T> mapperProxy) {
		return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(),
				new Class[]{this.mapperInterface},
				mapperProxy);
	}
}
