package com.bluedot.electrochemistry.service.sender;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public class MailSender extends AbstractSender {
    @Override
    public boolean sendMessage(String emailPath, String message) {
        //TODO 发送邮箱
        logger.info("发送邮件。。。");
        return true;
    }
}
