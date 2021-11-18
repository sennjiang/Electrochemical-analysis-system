package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/8/25-14:41
 */
public class Operation {
    /**
     * 日志id
     */
    private Integer id;
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
    private Integer user;
    /**
     * 日志 地点
     */
    private String recorder;
    /**
     * 日志类型
     */
    private Integer type;
    /**
     * 日志时间
     */
    private String time;
    /**
     * 是否为文件
     */
    private Integer isFile;
    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 日志编号
     */
    private String boundary;

    public Operation() {
    }

    public Operation(Integer id, String message, String level, Integer user, String recorder, Integer type, String time, Integer isFile, Integer fileType, String boundary) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getTime() {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(time);
        } catch (Exception ignored) {
        }
        return ts;
    }

    public void setTime(Timestamp time) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time = sdf.format(time);
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }
}
