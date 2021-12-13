package com.bluedot.electrochemistry.service.account.verify;

public class AccountVerify implements Verify {
    @Override
    public boolean verifyAccount(String account) {
        //TODO
        System.out.println("verifyAccount : " + account);
        return true;
    }
}
