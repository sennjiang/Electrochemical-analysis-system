package com.bluedot.electrochemistry.service.file.main;

import com.bluedot.electrochemistry.service.Lifecycle;
import com.bluedot.electrochemistry.service.file.processor.AbstractFileProcessor;

/**
 * @author Sens
 */
public abstract class FileModularity implements Lifecycle {

    private AbstractFileProcessor fileProcessor;

    /**
     * 文件上传
     */
    abstract void uploadFile();

    /**
     * 文件下载
     */
    abstract void exportFile();
}
