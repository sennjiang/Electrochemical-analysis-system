package com.bluedot.electrochemistry.pojo.domain;

/**
 * @ClassName Unfreeze
 * @Description 解冻申请实体类
 * @Version 1.0
 * @Author KangLongPing
 * @Date 2021/9/3 21:37
 **/
public class Unfreeze {
    private Integer id;
    private Integer freezeId;
    private Integer username;
    private String applicationTime;
    private String email;
    private Integer handleStatus;
    private String refuseReason;
    private String applicationReason;

    public Unfreeze() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFreezeId() {
        return freezeId;
    }

    public void setFreezeId(Integer freezeId) {
        this.freezeId = freezeId;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }
}
