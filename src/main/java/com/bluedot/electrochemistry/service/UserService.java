package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

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
		if(user != null && password.equals(user.getPassword())) {
			map.put("userInfo", user);
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
	 * 是否存在该用户
	 * @param map
	 */
	private void existUserByEmail(Map map) {
		String email = (String) map.get("email");
		BaseMapper mapper = mapperFactory.createMapper();
		Long userCount = mapper.countUserByEmail(email);
		if(userCount > 0) {
			map.put("code", 500);
			map.put("message", "用户已存在");
		}else {
			map.put("code", 200);
			map.put("message", "可以注册");
		}
	}

	private void sendEmail(Map map) throws Exception {
		// 创建Properties 类用于记录邮箱的一些属性
		Properties props = new Properties();
		// 表示SMTP发送邮件，必须进行身份验证
		props.put("mail.smtp.auth", "true");
		//此处填写SMTP服务器
		props.put("mail.smtp.host", "smtp.163.com");
		//端口号，QQ邮箱端口587
		props.put("mail.smtp.port", "25");
		// 此处填写，写信人的账号
		props.put("mail.user", "klpjxau@163.com");
		// 此处填写16位STMP口令
		props.put("mail.password", "NWDATKARUUTXXKJY");

		// 构建授权信息，用于进行SMTP进行身份验证
		Authenticator authenticator = new Authenticator() {

			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				// 用户名、密码
				String userName = props.getProperty("mail.user");
				String password = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, password);
			}
		};
		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(props, authenticator);
		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);
		// 设置发件人
		InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
		message.setFrom(form);

		// 设置收件人的邮箱
		InternetAddress to = new InternetAddress((String) map.get("email"));
		message.setRecipient(Message.RecipientType.TO, to);

		// 设置邮件标题
		message.setSubject("Hello");

		// 要发送的验证码
		String s = UUID.randomUUID().toString().replace("-", "").toLowerCase().substring(0, 5);

		// 设置邮件的内容体
		message.setContent("[电化学分析系统]你的验证码为：" + s, "text/html;charset=UTF-8");

		// 最后当然就是发送邮件啦
		Transport.send(message);

		map.put("code", "200");
		map.put("message", "邮件已发送");
	}

	/**
	 * 添加管理员
	 * @param map User实体类
	 */
	private void addAdmin(Map map) {
	}
}
