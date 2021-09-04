package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/8/25-14:41
 */
public class Operation {
    private int id;
    private String message;
    private String level;
    private int user;
    private String recorder;
    private short type;
    private Timestamp time;
    private Boolean isFile;
    private short FileType;

    public Operation() {
    }

    public Operation(int id, String message, String level, int user, String recorder, short type, Timestamp time, Boolean isFile, short fileType) {
        this.id = id;
        this.message = message;
        this.level = level;
        this.user = user;
        this.recorder = recorder;
        this.type = type;
        this.time = time;
        this.isFile = isFile;
        FileType = fileType;
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

    public short getFileType() {
        return FileType;
    }

    public void setFileType(short fileType) {
        FileType = fileType;
    }
}
