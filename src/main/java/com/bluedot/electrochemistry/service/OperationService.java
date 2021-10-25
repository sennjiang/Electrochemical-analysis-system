package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
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
            Integer type = Integer.parseInt((String) map.get("type"));
            String str = (String) map.get("username");
            Integer username = Integer.parseInt(str);
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String)  map.get("limit"));
            List<Operation> list = null;
            Long length = null;
            if (type == 1) {
                list = baseMapper.listOperationsByUser(type,username,(pageStart - 1) * pageSize,pageSize);
                length = baseMapper.countOperationsByUser(username,type);
            }else if(type == 2){
                list = baseMapper.listOperationsByAdmin(type,(pageStart - 1) * pageSize,pageSize);
                length = baseMapper.countAdminOperations(type);
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

    private void searchOperation(Map<String, Object> map) {
        try {
            BaseMapper mapper = mapperFactory.createMapper();

            String str = (String) map.get("username");
            int username = Integer.parseInt(str);
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String) map.get("limit"));
            short type = Short.parseShort((String) map.get("type"));
            String title = (String) map.get("title");
            List<Operation> files = null;
            Long size = null;
            if (type == 1) {
                files = mapper.searchOperationsByUser("%" + title + "%", username, type, (pageStart - 1) * pageSize, pageSize);
                size = mapper.countOperationsBySearchUser(("%" + title + "%"), username, type);
            } else {
                files = mapper.searchOperationsByAdmin("%" + title + "%","%" + title + "%", type, (pageStart - 1) * pageSize, pageSize);
                size = mapper.countOperationsByAdmin("%" + title + "%","%" + title + "%", type);
            }

            map.put("data", files);
            map.put("length", size);
            map.put("code", 200);
            map.put("message", "文件列表加载完成");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 401);
            map.put("message", "文件列表加载失败");
        }

    }
}
