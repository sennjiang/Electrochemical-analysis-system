package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.Map;

/**
 * @author 万梓豪
 */
@Service
public class UnfreezeService {

    @Autowired
    MapperFactory mapperFactory;

    /**
     * 更改解冻申请记录
     * @param map
     * @return 是否更新成功
     */
    private void updateUnfreeze(Map map) {

    }

    /**
     * 添加解冻申请记录
     * @param map
     * @return 是否添加成功
     */
    private void insertUnfreeze(Map map) {

    }
}
