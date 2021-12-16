package com.bluedot.electrochemistry.service.utils;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
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
