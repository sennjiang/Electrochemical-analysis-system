package com.bluedot.electrochemistry.service.sender;

import com.bluedot.framework.simplespring.util.LogUtil;
import org.slf4j.Logger;

/**
 * @author Sens
 */
public abstract class AbstractSender implements Sender {

    protected Logger logger = LogUtil.getLogger();

    @Override
    public boolean checkContent(String content) {
        return content.length() < 200 ;
    }
}
