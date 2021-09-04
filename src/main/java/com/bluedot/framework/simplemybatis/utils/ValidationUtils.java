package com.bluedot.framework.simplemybatis.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @className: ValidationUtils
 * @version: 1.0
 * @Description: 公共工具类
 * @Author: KangLongPing
 * @Date: 2021/8/21 021 15:20
 */
public class ValidationUtils {
	/**
	 * list/set is not empty
	 *
	 * @param collection 集合
	 * @return 结果
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * map is not empty
	 *
	 * @param map map
	 * @return 结果
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	/**
	 * 数组不为空
	 *
	 * @param objects 数组
	 * @return 结果
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return objects != null && objects.length > 0;
	}
}
