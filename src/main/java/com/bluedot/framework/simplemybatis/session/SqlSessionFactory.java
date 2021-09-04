package com.bluedot.framework.simplemybatis.session;

import java.io.IOException;
import java.io.InputStream;

/**
 * @className: SqlSessionFactory
 * @version: 1.0
 * @Description:
 * @Author: KangLongPing
 * @Date: 2021/8/21 021 14:58
 */
public interface SqlSessionFactory {
	/**
	 * 创建session对象，开始数据库会话
	 *
	 * @return 数据库会话对象
	 */
	SqlSession openSession();
}
