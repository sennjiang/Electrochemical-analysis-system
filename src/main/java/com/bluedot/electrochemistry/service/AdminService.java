package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.pojo.domain.UserRole;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/11 19:18
 */
@Service
public class AdminService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;
    /**
     * 查询管理员信息
     *
     */

    private void queryAdmins(Map<String, Object> map) {
        BaseMapper mapper = mapperFactory.createMapper();
        List<User> adminlist = mapper.getAdmins();
        map.put("data",adminlist);


    }

//    private void queryAdmins(Map<String , Object> map){
//        doSimpleQueryListTemplate(map, new ServiceCallback<User>() {
//            @Override
//            public List<User> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize) {
//                return baseMapper.getAdmins(pageStart,pageSize);
//            }
//
//            @Override
//            public List<User> doListExecutorByQueryCondition(BaseMapper baseMapper, int pageStart, int pageSize, String queryCondition, String queryValue) {
//                return baseMapper.getAdminByQueryCondition(queryCondition , queryValue , pageStart , pageSize);
//            }
//
//            @Override
//            public Long doCountExecutor(BaseMapper baseMapper) {
//                return baseMapper.getAdminCount();
//            }
//
//            @Override
//            public Long doCountExecutorByQueryCondition(BaseMapper baseMapper, String queryCondition, String queryValue) {
//                return baseMapper.getAdminCountByQueryCondition(queryCondition , queryValue);
//            }
//        });
//    }

    /**
     * 修改管理员的状态
     *
     * @param map
     */
    private void modifyAdminState(Map<String , Object> map){
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                return baseDao.update(packagingUser(map));
            }
        });
    }

    /**
     * 添加管理员，同时为该管理员添加一个管理员角色
     *
     * @param map
     */
    private void addAdmin(Map<String , Object> map){
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                User user = packagingUser(map);
                int addUserRole = baseDao.insert(new UserRole(user.getUsername(),200));
                int addUser = baseDao.insert(user);
                return addUser + addUserRole;
            }
        });
    }

    /**
     * 将请求数据中的信息封装成用户对象
     *
     * @param map
     * @return
     */
    private User packagingUser(Map<String , Object> map){
        Integer username = (Integer) map.get("username");
        String password = (String) map.get("password");
        String nickname  = (String) map.get("nickname");
        Integer gender = (Integer) map.get("gender");
        Integer age = (Integer) map.get("age");
        String email = (String) map.get("email");
        Timestamp birth = (Timestamp) map.get("birth");
        Integer status = (Integer) map.get("status");
        String portrait = (String) map.get("portrait");
        Timestamp gmtCreated = (Timestamp) map.get("gmtCreated");
        return new User(username,password,nickname,gender,age,email,birth,status,portrait,gmtCreated);

    }


    /**
     * 封装用户角色中间表的信息
     *
     * @param map
     * @return
     */
    private UserRole packagingUserRole(Map<String , Object> map){
        Integer username = (Integer) map.get("username");
        Integer roleId = (Integer) map.get("roleId");
        return new UserRole(username,roleId);
    }
}
