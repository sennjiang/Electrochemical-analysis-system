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
}
