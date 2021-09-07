package com.bluedot.electrochemistry.pojo.domain;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/3 19:22
 */
public class UserRole {
    //权限名称
    private Integer rightName;
    //用户名称
    private Integer username;

    private Integer trId;

    public UserRole() {
    }

    public UserRole(Integer rightName, Integer username, Integer trId) {
        this.rightName = rightName;
        this.username = username;
        this.trId = trId;
    }

    public Integer getRightName() {
        return rightName;
    }

    public void setRightName(Integer rightName) {
        this.rightName = rightName;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Integer getTrId() {
        return trId;
    }

    public void setTrId(Integer trId) {
        this.trId = trId;
    }
}
