package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/10/18 20:49
 */
public class LoginRightService {

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
        List<Integer> rightIdList = mapper.queryRightByUser(username);
        map.put("rightIdList",rightIdList);
    }
}
