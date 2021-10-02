package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Role;
import com.bluedot.electrochemistry.pojo.domain.RoleRight;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/9-17:55
 */
@Service
public class RoleService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;

    /**
     * 查询角色信息
     *
     * @param map
     */
    private void queryAdmins(Map<String,Object> map){
        BaseMapper mapper = mapperFactory.createMapper();
        List<Role> roleList = mapper.getRoles();
        map.put("data",roleList);
    }
//    private void queryRoles(Map<String , Object> map){
//        doSimpleQueryListTemplate(map, new ServiceCallback<Role>() {
//            @Override
//            public List<Role> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize) {
//                return baseMapper.getRoles(pageStart , pageSize);
//            }
//
//            @Override
//            public List<Role> doListExecutorByQueryCondition(BaseMapper baseMapper, int pageStart, int pageSize, String queryCondition, String queryValue) {
//                return baseMapper.getRoleByQueryCondition(queryCondition, queryValue, pageStart, pageSize);
//            }
//
//            @Override
//            public Long doCountExecutor(BaseMapper baseMapper) {
//                return baseMapper.getRoleCount();
//            }
//
//            @Override
//            public Long doCountExecutorByQueryCondition(BaseMapper baseMapper, String queryCondition, String queryValue) {
//                return baseMapper.getRoleCountByQueryCondition(queryCondition, queryValue);
//            }
//        });
//    }

    /**
     * 修改角色以及该角色拥有的菜单权限
     *
     * @param map
     * @return
     */
    private void modifyRole(Map<String , Object> map){
        doSimpleModifyTemplate(map, new ServiceCallback<Role>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                return baseDao.update(packagingRole(map));
            }
        });
    }

    /**
     * 添加角色，为角色赋予相应的权限
     *
     *
     */

    private void addRole(Map<String , Object> map){
        doSimpleModifyTemplate(map , new ServiceCallback<Role>(){
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                Role role = packagingRole(map);
                int addRoleRight = baseDao.insert(new RoleRight());
                int addRole = baseDao.insert(role);
                return addRole+addRoleRight;
            }
        });
    }

    //删除角色
    private void deleteRole(Map map){

    }

    private Role packagingRole(Map<String , Object> map){
        String roleName = (String) map.get("roleName");
        Timestamp genTime = (Timestamp) map.get("genTime");
        String description = (String) map.get("description");
        Integer rightType = Integer.valueOf((String) map.get("rightType"));

        return new Role(roleName , genTime , description , rightType);
    }

    private RoleRight packagingRoleRight(Map<String , Object> map){
        return new RoleRight();
    }
}
