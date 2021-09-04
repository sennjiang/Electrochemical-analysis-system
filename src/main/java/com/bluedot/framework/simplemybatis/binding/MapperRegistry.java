package com.bluedot.framework.simplemybatis.binding;

import com.bluedot.framework.simplemybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @version: v1.0
 * @className: MapperRegistry
 * @Description: mapper注册类
 * @Author: KangLongPing
 * @Date: 2021/8/21 8:20
 */
public class MapperRegistry {
	// 存储代理mapper工厂
	private final Map<Class<?>, MapperPoxyFactory<?>> knownMappers = new HashMap<>();

	/**
	 * 获取代理工厂实例
	 *
	 * @param type 被代理接口类的折类型, 作为key云代理工厂的map中寻找对象的工厂
	 * @param <T>  接口类泛型
	 */
	public <T> void addMapper(Class<T> type) {
		this.knownMappers.put(type, new MapperPoxyFactory<>(type));
	}

	/**
	 * 获取代理工厂实例
	 *
	 * @param sqlSession
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
		MapperPoxyFactory<T> mapperPoxyFactory = (MapperPoxyFactory<T>) this.knownMappers.get(type);
		return mapperPoxyFactory.newInstance(sqlSession);
	}
}
