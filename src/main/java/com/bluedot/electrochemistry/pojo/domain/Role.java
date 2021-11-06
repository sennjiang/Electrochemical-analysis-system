package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-21:07
 */
public class Role {
    //角色ID
    private Integer roleId;
    //角色名称
    private String roleName;
    //创建时间
    private Timestamp genTime;
    //角色描述
    private String description;
    //角色级别,0:管理员；1：普通用户
    private Integer roleType;

    public Role() {
    }

    public Role(String roleName, Timestamp genTime, String description, Integer roleType) {
        this.roleName = roleName;
        this.genTime = genTime;
        this.description = description;
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", genTime=" + genTime +
                ", description='" + description + '\'' +
                ", roleType=" + roleType +
                '}';
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Timestamp getGenTime() {
        return genTime;
    }

    public void setGenTime(Timestamp genTime) {
        this.genTime = genTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
