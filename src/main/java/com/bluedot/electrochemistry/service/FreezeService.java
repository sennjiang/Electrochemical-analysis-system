package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Freeze;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.Map;

@Service
public class FreezeService extends BaseService {
    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    BaseDao baseDao;

    /**
     * 添加冻结记录
     * @param map
     * @return 是否添加成功
     */
    private void insertFreeze(Map map) {
        try {
            Freeze freeze = new Freeze();
            freeze.setUsername(Integer.parseInt((String)map.get("username")));
            freeze.setFreezeReason((String)map.get("freezeReason"));
            baseDao.insert(freeze);
            map.put("code", 200);
        }
        catch (Exception e) {
            map.put("code", 500);
            map.put("message", "出现异常，添加冻结记录失败");
        }
    }
}
