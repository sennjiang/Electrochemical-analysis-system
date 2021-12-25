package com.bluedot.electrochemistry.service.file.main;

import com.bluedot.electrochemistry.service.Lifecycle;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public interface FileModularity extends Lifecycle {

    /**
     * 文件上传
     */
    void uploadFile();

    /**
     * 文件下载
     */
    void exportFile();


}
