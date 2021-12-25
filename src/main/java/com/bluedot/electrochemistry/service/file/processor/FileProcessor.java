package com.bluedot.electrochemistry.service.file.processor;

import java.io.File;

/**
 * 抽象 文件上传类
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public interface FileProcessor {

    /**
     * 文件上传类
     */
    void fileUploads();

    /**
     * 多文件上传处理
     * @return 成功 返回true 失败返回false
     */
    boolean multipleUploads(File[] files);

}
