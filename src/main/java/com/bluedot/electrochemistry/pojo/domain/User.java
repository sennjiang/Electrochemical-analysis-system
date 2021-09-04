package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @ClassName User
 * @Description User实体类; 有参构造方法中未提供对gmtModified参数的修改;
 * @Version 1.0
 * @Author KangLongPing
 * @Date 2021/9/3 20:21
 **/
public class User {
	// 用户名
	private String username;

	// 密码
	private String password;

	// 昵称
	private String nickname;

	// 性别
	private Integer gender;

	// 年龄
	private Integer age;

	// 邮箱
	private String email;

	// 出生日期
	private Timestamp birth;

	// 状态
	private Integer status;

	// 头像
	private String portrait;

	// 创建时间
	private Timestamp gmtCreated;

	// 修改时间
	private Timestamp gmtModified;

	public User() {
	}

	/**
	 * @return
	 * @Description 有参构造方法, 不提供对gmtModified参数的修改
	 * @Param [username 用户名, password 密码, nickname 昵称, gender 性别, age 年龄, email 邮件, birth 出生日期, status 状态, portrait 头像, gmtCreated 创建时间]
	 * @Author KangLongPing
	 * @Date 2021/9/4 14:50
	 **/
	public User(String username, String password, String nickname, Integer gender, Integer age, String email, Timestamp birth, Integer status, String portrait, Timestamp gmtCreated) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.birth = birth;
		this.status = status;
		this.portrait = portrait;
		this.gmtCreated = gmtCreated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getBirth() {
		return birth;
	}

	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public Timestamp getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Timestamp gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", gender=" + gender +
				", age=" + age +
				", email='" + email + '\'' +
				", birth=" + birth +
				", status=" + status +
				", portrait='" + portrait + '\'' +
				", gmtCreated=" + gmtCreated +
				", gmtModified=" + gmtModified +
				'}';
	}
}
