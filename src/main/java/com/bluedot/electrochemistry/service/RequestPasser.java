package com.bluedot.electrochemistry.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Sens
 * @createDate 2021/12/16 20:35
 */
public class RequestPasser implements Lifecycle{

    /**
     * 是否需要销毁 用于后期做 对象池操作
     */
    private boolean needDestroy = false;

    /**
     * 总编号
     */
    private int boundary;

    /**
     * 模块编号
     */
    private int serviceIndex;

    /**
     * 方法编号
     */
    private int methodIndex;

    /**
     * 用户 ID
     */
    private String username;

    /**
     * 参数
     */
    private Map<String, String> args;

    /**
     * 请求对象
     */
    private HttpServletRequest request;

    /**
     * 返回对象 用于返回数据
     */
    private Result result;

    public RequestPasser(int boundary, HttpServletRequest request) {
        this.boundary = boundary;
        this.request = request;
    }

    public int getBoundary() {
        return boundary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Result getResult() {
        return result;
    }

    private void newRequestPasser() {
        this.boundary = 0;
        this.username = null;
        this.serviceIndex = 0;
        this.methodIndex = 0;
        this.args = null;
        this.request = null;
        this.result = new Result();
    }

    /**
     * 对象初始化
     */
    @Override
    public void init() {
        newRequestPasser();
    }

    /**
     * 对象销毁
     */
    @Override
    public void destroy() {
        init();
    }
}
