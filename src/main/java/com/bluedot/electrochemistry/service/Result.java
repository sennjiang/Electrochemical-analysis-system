package com.bluedot.electrochemistry.service;

/**
 * @author Sens
 * @createDate 2021/12/16 20:36
 */
public class Result {
    /**
     * 存储 状态码 与 list大小 或其他数字
     * 按位存取
     */
    private int codeNum;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public void setCode(int num) {

    }

    public void setSize(int num) {

    }

    public int getCodeNum() {
        return codeNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
