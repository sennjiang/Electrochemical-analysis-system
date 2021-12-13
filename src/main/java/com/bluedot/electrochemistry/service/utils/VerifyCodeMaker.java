package com.bluedot.electrochemistry.service.utils;

/**
 * @author Sens
 */
public class VerifyCodeMaker {

    /**
     * 创建 验证码
     * @return 验证码
     */
    public String getVerifyCode() {
        //TODO
        return String.valueOf(Math.random() * 10000) ;
    }
}
