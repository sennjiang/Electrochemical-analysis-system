package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.electrochemistry.pojo.domain.Operation;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.core.annotation.Service;

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

    private BaseDao baseDao = (BaseDao) BeanContainer.getInstance().getBean(BaseDao.class);

    private BaseMapper baseMapper = (BaseMapper) BeanContainer.getInstance().getBean(BaseMapper.class);

    public void insertOperation(Operation operation) {
        baseDao.insert(operation);
    }

    private void listOperation(Map<String, Object> map) {
        try {
//            short type = (short) map.get("kind");
//            List<Operation> list = baseMapper.listOperations(type);
//            map.put("data",list);
            Operation operation1 = new Operation(1,"message","INFO",1,"UserService.login",
                    (short) 1,new Timestamp(System.currentTimeMillis()),false,0,"0101");
            Operation operation2 = new Operation(2,"message","WARN",1234567890,"FileService.export",
                    (short) 1,new Timestamp(System.currentTimeMillis()),true,1,"0101");
            List<Operation> list = new ArrayList<>();
            list.add(operation1);
            list.add(operation2);
            map.put("code",200);
            map.put("data",list);
            map.put("length",list.size());

        } catch (Exception e) {
            map.put("code",404);
            map.put("message",e.getMessage());
        }
    }
}
