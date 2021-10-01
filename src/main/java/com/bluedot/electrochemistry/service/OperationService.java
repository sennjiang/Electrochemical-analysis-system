package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.pojo.domain.Operation;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.mvc.monitor.Data;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/8/25-14:38
 */
@Service
public class OperationService extends BaseService {

    /**
     * 日志 type 映射表
     */
    private Map<String, Short> typeMapper = new HashMap<>();

    /**
     * 日志  file type 映射表
     */
    private Map<String, Integer> fileTypeMapper = new HashMap<>();


    private void insertOperation(Map map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                Data data = (Data) map;
                HttpServletRequest request = data.getRequest();

                User user = (User) request.getSession().getAttribute("user");
                Class service = data.getService();
                String name = service.getName();
                String serviceMethod = data.getServiceMethod();
                Operation operation = new Operation();
                if (typeMapper.isEmpty()) {
                    initFileMapper();
                }

                if (data.containsKey("message")) {
                    operation.setMessage((String) data.get("message"));
                }

                operation.setLevel(data.containsKey("error") ? "error" : "info");

                operation.setUser(user.getUsername());

                operation.setRecorder(name + "." + serviceMethod);

                String boundary = (String) data.get("boundary");
                operation.setType(data.containsKey(boundary) ? typeMapper.get(boundary) : (short) 1);

                operation.setTime(new Timestamp(System.currentTimeMillis()));

                operation.setBoundary(boundary);

                if ("FileService".equals(name)) {
                    operation.setFile(true);
                    if (fileTypeMapper.isEmpty()) initFileTypeMapper();
                    operation.setFileType(fileTypeMapper.get(boundary));
                }
                return baseDao.insert(operation);
            }
        });


    }

    /**
     * 初始化
     */
    private void initFileTypeMapper() {
        fileTypeMapper.put("0201",5);
        fileTypeMapper.put("0203",1);
        fileTypeMapper.put("0205",1);
        fileTypeMapper.put("0206",2);
        fileTypeMapper.put("0207",5);
        fileTypeMapper.put("0209",4);
        fileTypeMapper.put("0210",3);
        fileTypeMapper.put("0214",4);
    }

    /**
     * 初始化 mapper
     */
    private void initFileMapper() {
        typeMapper.put("0214", (short) 2);
        typeMapper.put("0404", (short) 2);
        typeMapper.put("0405", (short) 2);
        typeMapper.put("0406", (short) 2);
        typeMapper.put("0412", (short) 3);
        typeMapper.put("0413", (short) 3);
        typeMapper.put("0503", (short) 3);
        typeMapper.put("0504", (short) 3);
    }
}
