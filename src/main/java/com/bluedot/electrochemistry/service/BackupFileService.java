package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/11 19:31
 */
public class BackupFileService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;
    /**
     * 查询管理员信息
     *
     */
    private void queryAdmins(Map<String,Object> map){
        BaseMapper mapper = mapperFactory.createMapper();
        List<File> backupFileList = mapper.getBackupFiles();
        map.put("data",backupFileList);
    }

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
