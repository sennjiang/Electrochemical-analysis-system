package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/11 19:18
 */
@Service
public class AdminServicec extends BaseService {

    /**
     * 查询管理员信息
     *
     * @param map
     */
    private void queryAdmins(Map<String , Object> map){
        doSimpleQueryListTemplate(map, new ServiceCallback<User>() {
            @Override
            public List<User> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize) {
                return baseMapper.getAdmins(pageStart,pageSize);
            }

            @Override
            public List<User> doListExecutorByQueryCondition(BaseMapper baseMapper, int pageStart, int pageSize, String queryCondition, String queryValue) {
                return baseMapper.getAdminByQueryCondition(queryCondition , queryValue , pageStart , pageSize);
            }

            @Override
            public Long doCountExecutor(BaseMapper baseMapper) {
                return baseMapper.getAdminCount();
            }

            @Override
            public Long doCountExecutorByQueryCondition(BaseMapper baseMapper, String queryCondition, String queryValue) {
                return baseMapper.getAdminCountByQueryCondition(queryCondition , queryValue);
            }
        });
    }
}
