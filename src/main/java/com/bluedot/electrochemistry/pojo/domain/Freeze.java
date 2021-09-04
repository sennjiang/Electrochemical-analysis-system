package com.bluedot.electrochemistry.pojo.domain;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-21:07
 */
public class Freeze {
    private Integer freezeId;
    private Integer username;
    private String freezeReason;
    private String freezeTime;

    public Freeze() {
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

    public String getFreezeReason() {
        return freezeReason;
    }

    public void setFreezeReason(String freezeReason) {
        this.freezeReason = freezeReason;
    }

    public String getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(String freezeTime) {
        this.freezeTime = freezeTime;
    }
}
