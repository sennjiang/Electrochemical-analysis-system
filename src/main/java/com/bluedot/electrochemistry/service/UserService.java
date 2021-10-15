package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.Map;

/**
 * 用户业务
 * @version 1.0
 * @author KangLongPing
 * @date 2021/9/4 17:07
 **/
@Service
public class UserService extends BaseService {

	@Autowired
	MapperFactory mapperFactory;

	/**
	 * 登录方法
	 * @param map User实体类
	 */
	private void login(Map map) {
		int username = Integer.parseInt((String) map.get("username"));
		String password = (String) map.get("password");
		BaseMapper mapper = mapperFactory.createMapper();
		User user = mapper.queryUserByUsername(username);
		map.put("obj", user);
		if(user != null && password.equals(user.getPassword())) {
			map.put("code", 200);
			map.put("message", "登录成功");
		}else {
			map.put("code", 500);
			map.put("message", "账号或者密码错误");
		}
	}

	/**
	 * 根据用户名查询用户
	 * @param map User实体类
	 */
	private void queryUser(Map map) {
	}

	/**
	 * 修改用户个人信息
	 * @param map ResultBean实休类
	 */
	private void modifyUser(Map map) {
	}

	/**
	 * 添加用户
	 * @param map User实体类
	 */
	private void addUSer(Map map) {
	}

	/**
	 * 用户申请解冻
	 * @param map UnfreezeApplication实体类
	 */
	private void deleteUSerByUsername(Map map) {
	}

	/**
	 * 添加管理员
	 * @param map User实体类
	 */
	private void addAdmin(Map map) {
	}
}
