package com.bluedot.electrochemistry.pojo.vo;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-21:11
 */
public class UserRightView {
    //用户账号
    private Integer username;
    //权限名称
    private String rightName;

    public UserRightView() {
    }

    public UserRightView(Integer username, String rightName) {
        this.username = username;
        this.rightName = rightName;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    @Override
    public String toString() {
        return "UserRightView{" +
                "username=" + username +
                ", rightName='" + rightName + '\'' +
                '}';
    }
}
