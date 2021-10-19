package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Right;
import com.bluedot.electrochemistry.pojo.domain.RoleRight;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/10/18 20:49
 */
@Service
public class LoginRightService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;

    /**
     * 登录时查询该用户所拥有的权限
     *
     */
    private void queryRightByUser(Map<String , Object> map){
        String str = (String)(map.get("username"));
        int username = Integer.parseInt(str);
        BaseMapper mapper = mapperFactory.createMapper();
        List<RoleRight> rightIdList = mapper.queryRightByUser(username);
        System.out.println("查出:"+rightIdList);
        map.put("rightIdList",rightIdList);
    }
}
