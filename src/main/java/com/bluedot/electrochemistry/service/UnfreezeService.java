package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Unfreeze;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.List;
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


    private void searchUnfreeze(Map map) {
        try {
            BaseMapper mapper = mapperFactory.createMapper();
            int currentPage = Integer.parseInt((String) map.get("currentPage"));
            int pageSize = Integer.parseInt((String) map.get("pageSize"));
            currentPage = (currentPage - 1) * pageSize;
            String key = (String) map.get("key");
            if(key == null || key.length() == 0) {
                key = "";
            }
            List<Unfreeze> unfreezes =  mapper.queryUnfreezeByCurrent(key,currentPage,pageSize);
            long total = mapper.countUnfreezeByKey(key);
            map.put("total", total);
            map.put("unfreezes", unfreezes);
            map.put("code", 200);
        }
        catch (Exception e) {
            map.put("code", 500);
            map.put("message", "解冻申请查询失败");
        }
    }

    /**
     * 更改解冻申请记录
     * @param map
     * @return 是否更新成功
     */
    private void updateUnfreeze(Map map) {
        try {
            Unfreeze unfreeze = new Unfreeze();
            unfreeze.setId(Integer.parseInt((String)map.get("id")));
            String status = (String)map.get("handleStatus");
            if("1".equals(status)) {
                unfreeze.setHandleStatus(1);
            }else {
                unfreeze.setHandleStatus(2);
                unfreeze.setRefuseReason((String)map.get("refuseReason"));
            }
            String reason = (String) map.get("applicationReason");
            if(reason != null && reason.length() != 0) {
                unfreeze.setApplicationReason(reason);
            }
            baseDao.update(unfreeze);
            map.put("code", 200);
            if(!"1".equals(status)) {
                map.put("message", "操作成功");
            }
        }
        catch (Exception e) {
            map.put("code", 500);
            map.put("message", "出现异常，更新失败");
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
            unfreeze.setUsername(Integer.parseInt((String) map.get("username")));
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
