package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.pojo.domain.Operation;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.core.annotation.Service;

import java.util.List;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/8/25-14:38
 */
@Service
public class OperationService extends BaseService {

    private BaseDao baseDao = (BaseDao) BeanContainer.getInstance().getBean(BaseDao.class);

    private BaseMapper baseMapper = (BaseMapper) BeanContainer.getInstance().getBean(BaseMapper.class);

    public void insertOperation(Operation operation) {
        baseDao.insert(operation);
    }

    private void listOperation(Map<String, Object> map) {
        try {
            short type = (short) map.get("type");
            List<Operation> list = baseMapper.listOperations(type);
            map.put("data",list);
        } catch (Exception e) {
            map.put("code",404);
            map.put("message",e.getMessage());
        }
    }
}
