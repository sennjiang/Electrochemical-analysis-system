package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Unfreeze;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.Map;

/**
 * @author 万梓豪
 */
@Service
public class UnfreezeService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    BaseDao baseDao;

    /**
     * 更改解冻申请记录
     * @param map
     * @return 是否更新成功
     */
    private void updateUnfreeze(Map map) {
        try {
            Unfreeze unfreeze = new Unfreeze();
            unfreeze.setUsername(Long.parseLong((String) map.get("username")));
            unfreeze.setFreezeId(Integer.parseInt((String)map.get("freezeId")));
            if("1".equals((String)map.get("handleStatus"))) {
                unfreeze.setHandleStatus(1);
            }else {
                unfreeze.setHandleStatus(2);
                unfreeze.setRefuseReason((String)map.get("refuseReason"));
            }
            unfreeze.setApplicationReason((String) map.get("applicationReason"));
            unfreeze.setEmail((String) map.get("email"));
            baseDao.insert(unfreeze);
            baseDao.update(unfreeze);
            map.put("code", 200);
            map.put("message", "更新解冻记录成功");
        }
        catch (Exception e) {
            map.put("code", 500);
            map.put("message", "出现异常，更新解冻记录失败");
        }
    }

    /**
     * 添加解冻申请记录
     * @param map
     * @return 是否添加成功
     */
    private void insertUnfreeze(Map map) {
        try {
            Unfreeze unfreeze = new Unfreeze();
            unfreeze.setUsername(Long.parseLong((String) map.get("username")));
            unfreeze.setFreezeId(Integer.parseInt((String)map.get("freezeId")));
            unfreeze.setHandleStatus(0);
            unfreeze.setApplicationReason((String) map.get("applicationReason"));
            unfreeze.setEmail((String) map.get("email"));
            baseDao.insert(unfreeze);
            map.put("code", 200);
            map.put("message", "申请成功");
        }
        catch (Exception e) {
            map.put("code", 500);
            map.put("message", "出现未知异常，申请失败");
        }
    }
}
