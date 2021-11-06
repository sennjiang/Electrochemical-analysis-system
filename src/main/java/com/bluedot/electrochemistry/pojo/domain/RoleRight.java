package com.bluedot.electrochemistry.pojo.domain;


/**
 * @author 万梓豪
 */
public class RoleRight {
    private Integer rrId;
    private Integer roleId;
    private Integer rightId;


    public RoleRight() {
    }

    public RoleRight(Integer roleId, Integer rightId, Integer rightType) {
        this.rrId = rrId;
        this.roleId = roleId;
        this.rightId = rightId;
    }

    public RoleRight(Integer roleId, Integer rightId) {
        this.roleId = roleId;
        this.rightId = rightId;
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

    @Override
    public String toString() {
        return "RoleRight{" +
                "rrId=" + rrId +
                ", roleId=" + roleId +
                ", rightId=" + rightId +
                '}';
    }
}
