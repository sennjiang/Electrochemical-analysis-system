package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.*;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;
import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import org.w3c.dom.ls.LSInput;

import javax.management.loading.MLetContent;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class RoleService extends BaseService{

    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    BaseDao baseDao;

    /**
     * 查询角色信息
     *
     * @param map
     */
    private void queryRoles(Map<String,Object> map){
        try{
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

            //查询当前角色拥有的权限
            List<List<String>> rightNames = new ArrayList<List<String>>();
            //遍历角色
            for(Role role:roleList){
                //角色列表
                List<Right> rights = mapper.getRights(role.getRoleId());
                //角色权限列表
                List<String> rightNameList = new ArrayList<>();
                for(Right right:rights){
                    rightNameList.add(right.getRightName());
                }
                rightNames.add(new ArrayList<>(rightNameList));
            }

            System.out.println(rightNames.toString());

            map.put("rightNames",rightNames);
            map.put("data",roleList);
            map.put("numbers",numbers);

            map.put("code",200);
            map.put("message","角色列表加载成功");

        }catch (Exception e){
            map.put("code",500);
            map.put("message","角色列表加载失败");
        }
    }

//    /**
//     *
//     * 查询角色对应的权限
//     * @param map
//     */
//    private void queryRights(Map<String,Object> map){
//        BaseMapper mapper = mapperFactory.createMapper();
//        String query = (String) map.get("query");
//
//        List<Right> rightList = new ArrayList<>();
//        if(query.equals("管理员")){
//            rightList = mapper.getRights(200);
//        }else{
//            rightList = mapper.getRights(300);
//        }
//
//        map.put("data",rightList);
//    }
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

//    /**
//     * 修改角色以及该角色拥有的菜单权限
//     *
//     * @param map
//     * @return
//     */
//    private void modifyRole(Map<String , Object> map){
//        doSimpleModifyTemplate(map, new ServiceCallback<Role>() {
//            @Override
//            public int doDataModifyExecutor(BaseDao baseDao) {
//                return baseDao.update(packagingRole(map));
//            }
//        });
//    }

    /**
     * 添加角色，为角色赋予相应的权限
     *
     *
     */
    private void addRole(Map<String,Object> map ){

        try{
            Role role = packagingRole(map);
            //添加角色
            int addRole = baseDao.insert(role);
            //查询刚添加的角色的roleId
            BaseMapper mapper = mapperFactory.createMapper();
            Role role1 = mapper.queryNewRoleId(role.getRoleName());

            //将前端传过来的right_name数组取出来
            Data data = (Data) map;
            HttpServletRequest request = data.getRequest();
            String[] checkRightLists = request.getParameterValues("checkRightList");

            //根据right_name数组查询出right列表取出right_id数组
            int[] rightIdList = new int[checkRightLists.length];
            int index = -1;
            for(String rightName:checkRightLists){
                //System.out.println(rightName);
                Right right = mapper.getRightByRightName(rightName);
                rightIdList[++index] = right.getRightId();
            }

            //将新角色的Id与勾选的权限Id组成一个新的roleRight角色权限对象，将该对象加入数据库
            for(int rightId : rightIdList){
                //System.out.println(rightId);
                RoleRight roleRight = new RoleRight(role1.getRoleId(),rightId);
                int addRoleRight = baseDao.insert(roleRight);
            }

            map.put("code",200);
            map.put("message","角色创建成功");
        }catch (Exception e){
            map.put("code",500);
            map.put("message","角色创建失败");
        }



    }

    //删除角色
    private void deleteRole(Map map){

    }

    private Role packagingRole(Map<String , Object> map){
        String roleName = (String) map.get("roleName");
        Timestamp genTime = new Timestamp(new Date().getTime());
        String description = (String) map.get("description");
        Integer rightType = Integer.valueOf((String) map.get("roleType"));
        return new Role(roleName , genTime , description , rightType);
    }

    private RoleRight packagingRoleRight(Map<String , Object> map){
        return new RoleRight();
    }
}
