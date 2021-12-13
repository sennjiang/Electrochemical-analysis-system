package com.bluedot.electrochemistry.service.account.main;

import com.bluedot.electrochemistry.service.Lifecycle;
import com.bluedot.electrochemistry.service.account.verify.Verify;
import com.bluedot.electrochemistry.service.sender.Sender;
import com.bluedot.electrochemistry.service.utils.VerifyCodeMaker;

/**
 * @author Sens
 * @param <T> 用户类
 */
public abstract class AbstractAccountModularity<T> implements Lifecycle {

    protected VerifyCodeMaker verifyCodeMaker;

    //发送接口 实现
    protected Sender sender;

    /**
     * 发送 验证码
     * @param account 用户
     * @return String 验证码
     */
    protected abstract String sendVerifyCode(String account) ;

    /**
     * 验证用户是否存在
     * @param account 用户
     * @return boolean 是否存在
     */
    protected abstract boolean verifyAccount(String account, Verify verify);

    /**
     * 登录
     * @return T 用户实体类
     */
    protected abstract T login(String account, String password);

    /**
     * 注册
     * @param t 用户实体类
     */
    protected abstract void register(T t);

    /**
     * 修改个人信息
     * @param t 用户实体类
     */
    protected abstract void updateAccountInfo(T t);

}
