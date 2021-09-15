package com.bluedot.electrochemistry.pojo.domain;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/3 19:22
 */
public class UserRole {

    private Integer userRoleId;


    //用户名称
    private Integer username;

    //角色名称
    private Integer roleId;




    public UserRole() {
    }

    public UserRole(Integer username, Integer roleId) {
        this.username = username;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userRoleId=" + userRoleId +
                ", roleId=" + roleId +
                ", username=" + username +
                '}';
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }
}
