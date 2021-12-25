package com.bluedot.electrochemistry.service.file.processor;

import java.io.File;

/**
 * @author Sens
 * @createDate 2021/12/17 20:02
 */
public abstract class AbstractFileProcessor implements FileProcessor{

    @Override
    public boolean multipleUploads(File[] files) {
        for (File file : files) {
            fileUploads();
        }
        return false;
    }
}
