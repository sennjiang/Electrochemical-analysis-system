package com.bluedot.electrochemistry.pojo.vo;

import java.sql.Timestamp;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-21:10
 */
public class FileDetailView {
    /**
     * 文件id
     */
    private int id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件owner
     */
    private int owner;
    /**
     * 文件大小
     */
    private double size;
    /**
     * 文件状态
     */
    private short status;
    /**
     * 文件创建时间
     */
    private Timestamp produceTime;
    /**
     * 文件处理时间
     */
    private Timestamp modifiedTime;
    private double dataStart;
    /**
     * 文件数据结束点
     */
    private double dataEnd;
    /**
     * 文件数据最小值
     */
    private double dataBottom;
    /**
     * 文件数据最大值
     */
    private double dataPeak;
    /**
     * 文件数据精度
     */
    private double dataPrecision;
    /*
     * 昵称
     */
    private String nickname;
}
