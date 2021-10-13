package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.Map;

@Service
public class FreezeService {
    @Autowired
    MapperFactory mapperFactory;

    /**
     * 添加冻结记录
     * @param map
     * @return 是否添加成功
     */
    private void insertFreeze(Map map) {

    }
}
