package com.bluedot.electrochemistry.service.file.processor;

/**
 * 抽象 文件上传类
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public abstract class AbstractFileProcessor {

    /**
     * 文件上传类
     */
    protected abstract void fileUploads();

    protected boolean multipleUploads() {
        //TODO 多文件处理的方法
        return false;
    }

}
