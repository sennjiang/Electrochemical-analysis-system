package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Right;
import com.bluedot.electrochemistry.pojo.domain.Role;
import com.bluedot.electrochemistry.pojo.domain.RoleRight;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
    private void queryRoles(Map<String,Object> map){
        BaseMapper mapper = mapperFactory.createMapper();
        List<Role> roleList = new ArrayList<>();

        Integer pageNum = Integer.parseInt((String) map.get("pageNum"));
        Integer pageSize = Integer.parseInt((String) map.get("pageSize"));
        //获取当前编号
        int pageStart = (pageNum-1)*pageSize;

        long numbers = 0 ;
        String query = (String) map.get("query");
        if(query.equals("")){
            numbers = mapper.getRoleCount();
            roleList = mapper.getRoles(pageStart,pageSize);
        }else {
            numbers = mapper.getRoleCountByQuery("%"+query+"%");
            roleList = mapper.getRoleByQuery("%"+query+"%",pageStart,pageSize);
        }

        map.put("data",roleList);
        map.put("numbers",numbers);
    }


    private void queryRights(Map<String,Object> map){
        BaseMapper mapper = mapperFactory.createMapper();
        String query = (String) map.get("query");

        List<Right> rightList = new ArrayList<>();
        if(query.equals("管理员")){
            rightList = mapper.getRights(200);
        }else{
            rightList = mapper.getRights(300);
        }

        map.put("data",rightList);
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
                //添加角色
                int addRole = baseDao.insert(role);
                //查询刚添加的角色的roleId
                BaseMapper mapper = mapperFactory.createMapper();
                Integer roleId = mapper.queryNewRoleId(role.getRoleName());


                List<Right> rights = (List<Right>) map.get("checkRightList");
                for(Right right : rights){
                    RoleRight roleRight = new RoleRight(roleId,right.getRightId());
                    int addRight = baseDao.insert(roleRight);
                }

                map.put("data",addRole);
                return addRole;
            }
        });
    }

    //删除角色
    private void deleteRole(Map map){

    }

    private Role packagingRole(Map<String , Object> map){
        String roleName = (String) map.get("roleName");
        Timestamp genTime = (Timestamp) new Date();
        String description = (String) map.get("description");
        Integer rightType = Integer.valueOf((String) map.get("roleType"));
        return new Role(roleName , genTime , description , rightType);
    }

    private RoleRight packagingRoleRight(Map<String , Object> map){
        return new RoleRight();
    }
}
