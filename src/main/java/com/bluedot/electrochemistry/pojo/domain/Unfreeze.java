package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @ClassName Unfreeze
 * @Description 解冻申请实体类
 * @Version 1.0
 * @Author KangLongPing
 * @Date 2021/9/3 21:37
 **/
public class Unfreeze {
    // id
    private Integer id;
    // 冻结表id
    private Integer freezeId;
    // todo
    private Integer reviewer;
    // 用户名
    private Integer username;
    // 申请时间
    private Timestamp applicationTime;
    // 邮箱
    private String email;
    // 处理状态
    private Integer handleStatus;
    // 拒绝原因
    private String refuseReason;
    // 申请理由
    private String applicationReason;
    // 记录修改时间
    private Timestamp gmtModified;

    public Unfreeze() {
    }

    /**
     * @Return
     * @Description 未提供gmt_modified的时间
     * @Param [id, freezeId, reviewer, username, applicationTime, email, handleStatus, refuseReason, applicationReason]
     * @Date 2021/9/5 10:22
     **/
    public Unfreeze(Integer id, Integer freezeId, Integer reviewer, Integer username, Timestamp applicationTime, String email, Integer handleStatus, String refuseReason, String applicationReason) {
        this.id = id;
        this.freezeId = freezeId;
        this.reviewer = reviewer;
        this.username = username;
        this.applicationTime = applicationTime;
        this.email = email;
        this.handleStatus = handleStatus;
        this.refuseReason = refuseReason;
        this.applicationReason = applicationReason;
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

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
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

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }
}
