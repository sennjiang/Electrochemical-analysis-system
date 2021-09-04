package com.bluedot.framework.simplemybatis.bean;

/**
 * @version: 1.0.0
 * @className: ColumnInfo
 * @Description: 封装数据库表的一个字段的信息
 * @Author: KangLongPing
 * @Date: 2021/8/20 9:13
 */
public class ColumnInfo {

    // 字段名
    private String name;

    // 字段数据类型
    private String dateType;

    // 字段的键类型(规定 0:普通键，1：主键，2：外键)
    private int keyType;

    public ColumnInfo() {
    }


    public ColumnInfo(String name, String dateType, int keyType) {
        this.name = name;
        this.dateType = dateType;
        this.keyType = keyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "name='" + name + '\'' +
                ", dateType='" + dateType + '\'' +
                ", keyType=" + keyType +
                '}';
    }
}
