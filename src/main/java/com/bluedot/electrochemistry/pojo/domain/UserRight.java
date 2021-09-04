package com.bluedot.electrochemistry.pojo.domain;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/3 19:22
 */
public class UserRight {
    //权限名称
    private String rightName;
    //用户名称
    private String username;

    public UserRight() {
    }

    public UserRight(String rightName, String username) {
        this.rightName = rightName;
        this.username = username;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserRight{" +
                "rightName='" + rightName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
