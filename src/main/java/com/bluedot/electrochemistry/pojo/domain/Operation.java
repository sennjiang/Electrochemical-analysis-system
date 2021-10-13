package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/8/25-14:41
 */
public class Operation {
    /**
     * 日志id
     */
    private int id;
    /**
     * 日志信息
     */
    private String message;
    /**
     * 日志级别
     */
    private String level;
    /**
     * 日志user
     */
    private int user;
    /**
     * 日志 地点
     */
    private String recorder;
    /**
     * 日志类型
     */
    private short type;
    /**
     * 日志时间
     */
    private Timestamp time;
    /**
     * 是否为文件
     */
    private Boolean isFile;
    /**
     * 文件类型
     */
    private int fileType;

    /**
     * 日志编号
     */
    private String boundary;

    public Operation() {
    }

    public Operation(int id, String message, String level, int user, String recorder, short type, Timestamp time, boolean isFile, int fileType, String boundary) {
        this.id = id;
        this.message = message;
        this.level = level;
        this.user = user;
        this.recorder = recorder;
        this.type = type;
        this.time = time;
        this.isFile = isFile;
        this.fileType = fileType;
        this.boundary = boundary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }
}
