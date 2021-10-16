package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Operation;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/8/25-14:38
 */
@Service
public class OperationService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;

    private BaseDao baseDao = (BaseDao) BeanContainer.getInstance().getBean(BaseDao.class);

    public void insertOperation(Operation operation) {
        baseDao.insert(operation);
    }

    private void listOperation(Map<String, Object> map) {
        BaseMapper baseMapper = mapperFactory.createMapper();
        try {
            Integer type = Integer.parseInt((String) map.get("kind"));
            String str = (String) map.get("username");
            Integer username = Integer.parseInt(str);
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String)  map.get("limit"));
            List<Operation> list = null;
            Long length = null;
            if (type == 1) {
                list = baseMapper.listOperationsByUser(type,username,(pageStart - 1) * pageSize,pageSize);
                length = baseMapper.countOperationsByUser(type,username);
            }else {
                list = baseMapper.listOperations(type,(pageStart - 1) * pageSize,pageSize);
                length = baseMapper.countOperations(type);
            }
            map.put("data",list);
            map.put("code",200);
            map.put("length",length);

        } catch (Exception e) {
            map.put("code",404);
            map.put("message",e.getMessage());
        }
    }
}
