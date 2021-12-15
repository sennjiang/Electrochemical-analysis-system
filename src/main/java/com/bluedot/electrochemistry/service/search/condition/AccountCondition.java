package com.bluedot.electrochemistry.service.search.condition;

import com.bluedot.electrochemistry.service.search.AbstractCondition;

/**
 * @author Sens
 * @Date 2021/12/15 17:08
 */
public class AccountCondition extends AbstractCondition {

    private String username;


    @Override
    public String decodeCondition() {
        checkCondition();
        return "";
    }

    @Override
    public boolean checkCondition() {
        return false;
    }

}
