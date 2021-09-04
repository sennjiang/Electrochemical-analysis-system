package com.bluedot.electrochemistry.service;

import java.util.Map;

/**
 * 算法审核业务
 * @author zero
 */
public class AlgorithmSendService {

    /**
     * 获取所有算法申请集合
     * @author zero
     * @param map map中包含的参数：
     *      algorithmSendId算法申请的id
     *      algorithmId被审核的原(旧)算法的id
     *      type：申请的类别，0添加，-1删除，>0修改
     *      isPass：审核通过与否，0不通过，1通过
     * 审核成功与否的结果会放入到map中去，0失败，1成功
     */
    private void  judgementAlgorithmSend(Map map){}
}
