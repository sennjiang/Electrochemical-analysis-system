package com.bluedot.electrochemistry.service.account.main;

import com.bluedot.electrochemistry.service.Lifecycle;
import com.bluedot.electrochemistry.service.account.verify.Verify;
import com.bluedot.electrochemistry.service.sender.Sender;
import com.bluedot.electrochemistry.service.utils.VerifyCodeMaker;

/**
 * @author Sens
 * @param <T> 用户类
 */
public interface AbstractAccountModularity<T> extends Lifecycle {

    /**
     * 发送 验证码
     * @param account 用户
     * @return String 验证码
     */
    String sendVerifyCode(String account) ;

    /**
     * 验证用户是否存在
     * @param account 用户 账号 邮箱 或其他
     * @return boolean 是否存在
     */
    boolean verifyAccount(String account, Verify verify);

    /**
     * 登录
     * @return T 用户实体类
     */
    T login(String account, String password);

    /**
     * 注册
     * @param t 用户实体类
     */
    void register(T t);

    /**
     * 修改个人信息
     * @param t 用户实体类
     */
    void updateAccountInfo(T t);

    /**
     * 创建用户
     * @param t 泛型
     */
    void addAccount(T t);

}
