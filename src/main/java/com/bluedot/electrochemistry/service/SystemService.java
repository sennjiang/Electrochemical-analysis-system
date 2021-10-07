package com.bluedot.electrochemistry.service;

import com.bluedot.framework.simplespring.core.annotation.Service;

import java.io.File;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/3 19:29
 */
@Service
public class SystemService {

    //系统还原
    private int systemReduction(Map map){
        return 1;
    }

    //系统备份
    private int systemBackup(Map map){
        return 1;
    }

    //下载备份文件压缩包
    private File downloadBackupFile(Map map){
        return new File("1","1");
    }

    //删除当前的FTP
    private int deleteFTP(Map map){
        return 1;
    }


}
