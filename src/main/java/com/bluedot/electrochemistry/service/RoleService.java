package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.pojo.domain.Role;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;

import java.util.List;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/9-17:55
 */
@Service
public class RoleService extends BaseService {


    /**
     * 查询角色信息
     *
     * @param map
     */
    private void queryRoles(Map<String , Object> map){
        doSimpleQueryListTemplate(map, new ServiceCallback<Role>() {
            @Override
            public List<Role> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize) {
                return baseMapper.getRoles(pageStart , pageSize);
            }

            @Override
            public List<Role> doListExecutorByQueryCondition(BaseMapper baseMapper, int pageStart, int pageSize, String queryCondition, String queryValue) {
                return baseMapper.getRoleByQueryCondition(queryCondition, queryValue, pageStart, pageSize);
            }

            @Override
            public Long doCountExecutor(BaseMapper baseMapper) {
                return baseMapper.getRoleCount();
            }

            @Override
            public Long doCountExecutorByQueryCondition(BaseMapper baseMapper, String queryCondition, String queryValue) {
                return baseMapper.getRoleCountByQueryCondition(queryCondition, queryValue);
            }
        });
    }

    //修改角色以及该角色拥有的菜单权限
    private int modifyRole(Map map){
        return 1;
    }

    //添加角色，为角色赋予相应的权限
    private int addRole(Map map){
        return 1;
    }

    //删除角色
    private int deleteRole(Map map){
        return 1;
    }
}
