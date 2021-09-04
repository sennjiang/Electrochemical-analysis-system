package com.bluedot.electrochemistry.pojo.domain;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-21:07
 */
public class Right {
    //权限ID
    private Integer rightId;
    //权限名称
    private String rightName;
    //权限描述
    private String description;
    //权限路径
    private String rightUrl;
    //权限所属角色级别，0: 普通用户 1: 管理员 2:超级管理员
    private Integer rightLevel;

    public Right() {
    }

    public Right(String rightName, String description, String rightUrl, Integer rightLevel) {
        this.rightName = rightName;
        this.description = description;
        this.rightUrl = rightUrl;
        this.rightLevel = rightLevel;
    }

    @Override
    public String toString() {
        return "Right{" +
                "rightId=" + rightId +
                ", rightName='" + rightName + '\'' +
                ", description='" + description + '\'' +
                ", rightUrl='" + rightUrl + '\'' +
                ", rightLevel=" + rightLevel +
                '}';
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public void setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl;
    }

    public Integer getRightLevel() {
        return rightLevel;
    }

    public void setRightLevel(Integer rightLevel) {
        this.rightLevel = rightLevel;
    }
}
