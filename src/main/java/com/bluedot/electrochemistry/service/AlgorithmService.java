package com.bluedot.electrochemistry.service;

import java.util.Map;

/**
 * 算法业务
 * @author zero
 */
public class AlgorithmService {
    /**
     * 加载文件中的数据
     * @author zero
     * @param map 包含algorithmId被加载的算法的id
     * 会将加载完数据的算法实体在函数体内放入到map中去
     */
    private void loadingData(Map map){}

    /**
     * 修改算法
     * @author zero
     * @param map map中包含参数：
     *      algorithm被修改后的算法实体
     *      username当前修改的用户的账号
     *      algorithmId被修改之前的旧算法的id
     * 修改成功与否的结果会放入到map中去，0失败，1成功
     */
    private void updateAlgorithm(Map map){}

    /**
     * 添加算法
     * @author zero
     * @param map map中包含参数：
     *      algorithm被添加的算法实体
     *      username当前修改的用户的账号
     * 添加成功与否的结果会放入到map中去，0失败，1成功
     */
    private void addAlgorithm(Map map){}

    /**
     * 删除算法
     * @author zero
     * @param map map中包含参数：
     *      algorithmId被删除的算法的id
     *      username当前修改的用户的账号
     * 删除成功与否的结果会放入到map中去，0失败，1成功
     */
    private void deleteAlgorithm(Map map){}
}
