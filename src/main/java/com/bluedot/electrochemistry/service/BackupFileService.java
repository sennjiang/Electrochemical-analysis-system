package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/11 19:31
 */
public class BackupFileService extends BaseService {

//    private void queryBackupFiles(Map<String , Object> map){
//        doSimpleQueryListTemplate(map, new ServiceCallback<File>() {
//            @Override
//            public List<File> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize) {
//                return baseMapper.getBackupFiles(pageStart , pageSize);
//            }
//
//            @Override
//            public List<File> doListExecutorByQueryCondition(BaseMapper baseMapper, int pageStart, int pageSize, String queryCondition, String queryValue) {
//                return baseMapper.getBackupFileByQueryCondition(queryCondition , queryValue , pageStart , pageSize);
//            }
//
//            @Override
//            public Long doCountExecutor(BaseMapper baseMapper) {
//                return baseMapper.getBackupFileCount();
//            }
//
//            @Override
//            public Long doCountExecutorByQueryCondition(BaseMapper baseMapper, String queryCondition, String queryValue) {
//                return baseMapper.getBackupFileCountByQueryCondition(queryCondition , queryValue);
//            }
//        });
//    }
}
