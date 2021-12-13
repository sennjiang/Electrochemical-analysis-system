package com.bluedot.electrochemistry.service.sender;

/**
 * @author Sens
 */
public interface Sender {
    //发送消息
    boolean sendMessage(String emailPath, String message);
    //内容验证
    boolean checkContent(String content);
}
