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
import java.util.ArrayList;
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
        List<User> adminlist = new ArrayList<>();

        Integer pageNum = Integer.parseInt((String) map.get("pageNum"));
        Integer pageSize = Integer.parseInt((String) map.get("pageSize"));
        //获取当前编号
        int pageStart = (pageNum-1)*pageSize;


        //System.out.println(map.get("query"));
        //query为""则查询所有用户，否则支持模糊查询
        long numbers = 0 ;
        if(map.get("query").equals("")){
            numbers = mapper.getAdminCount();
            adminlist = mapper.getAdmins(pageStart,pageSize);
        }else {
            numbers = mapper.getAdminCountByQuery((String)map.get("query"));
            adminlist = mapper.getAdminsByQuery((String) map.get("query"),pageStart,pageSize);
        }

        map.put("data",adminlist);
        map.put("numbers",numbers);


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
                int update = baseDao.update(packagingUser(map));
                //System.out.println(update);
                map.put("data",update);
                return update;
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

                map.put("data",addUserRole+addUser);
                return addUserRole+addUser;
            }
        });
    }

    /**
     *
     * 删除该用户的管理员角色
     * @param map
     */
    private void deleteAdmin(Map<String , Object> map){

        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                //查询该用户的管理员角色的ID
                User user = packagingUser(map);
                BaseMapper mapper = mapperFactory.createMapper();
                UserRole userRole = mapper.getUserRoleId(user.getUsername(),200);
                System.out.println(userRole.getUserRoleId());

                //UserRole userRole = new UserRole(user.getUsername(),200);

                int deleteUserRole = baseDao.delete(userRole);

                map.put("data",deleteUserRole);
                return deleteUserRole;
            }
        });
    }

    private void queryEditAdmin(Map<String, Object> map){
        BaseMapper mapper = mapperFactory.createMapper();
        User user = mapper.getQueryEditAdmin(Integer.parseInt((String) map.get("username")));

        map.put("data",user);
    }

    /**
     * 将请求数据中的信息封装成用户对象
     *
     * @param map
     * @return
     */
    private User packagingUser(Map<String , Object> map){
        Integer username = Integer.parseInt((String) map.get("username"));
        String password = (String) map.get("password");
        String nickname  = (String) map.get("nickname");
        Integer gender = (Integer) map.get("gender");
        Integer age = (Integer) map.get("age");
        String email = (String) map.get("email");
        Timestamp birth = (Timestamp) map.get("birth");

        String statusTmp = (String)map.get("status");
        //添加用户时statusTmp == null，给status赋初值
        Integer status = -1;
        if(statusTmp == null) status=1;
        else status = statusTmp.equals("true")?1:0;

        String portrait = (String) map.get("portrait");
        Timestamp gmtCreated = (Timestamp) map.get("gmtCreated");
        return new User(username,password,nickname,gender,age,email,birth,status,portrait,gmtCreated);

    }


    private void editAdmin(Map<String , Object> map){
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                User user = packagingUser(map);

                int editAdmin = baseDao.update(user);
                map.put("data",editAdmin);
                return editAdmin;
            }
        });
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
