package com.bluedot.electrochemistry.pojo.domain;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/6-20:27
 */
public class RoleRight {
    private Integer rrId;
    private Integer roleId;
    private Integer rightId;
    private Integer rightType;

    public RoleRight() {
    }

    public RoleRight(Integer rrId, Integer roleId, Integer rightId, Integer rightType) {
        this.rrId = rrId;
        this.roleId = roleId;
        this.rightId = rightId;
        this.rightType = rightType;
    }

    public Integer getRrId() {
        return rrId;
    }

    public void setRrId(Integer rrId) {
        this.rrId = rrId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public Integer getRightType() {
        return rightType;
    }

    public void setRightType(Integer rightType) {
        this.rightType = rightType;
    }
}
