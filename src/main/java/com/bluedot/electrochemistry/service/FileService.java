package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.service.base.BaseService;

import java.util.Map;

/**
 * @description
 * @createDate 2021/8/25-14:37
 */
public class FileService extends BaseService {

    /**
     * 导出文件
     * @param map
     */
    private void export(Map map) {
        System.out.println("你好啊");
    }

    /**
     * 根据文件编号去查询文件
     * @param map
     */
    private void findFile(Map map) {}

    /**
     * 上传文件
     * @param map
     */
    private void uploadFile(Map map) {}

    /**
     * 删除文件，文件彻底删除
     * @param map
     */
    private void deleteFile(Map map) {}

    /**
     * 移除文件 文件进入回收站
     * @param map
     */
    private void remove(Map map) {}

    /**
     * 还原文件，文件从回收站还原
     * @param map
     */
    private void restore(Map map) {}

    /**
     * 更新文件
     * @param map
     */
    private void updateFile(Map map) {}

    /**
     * 比较文件hash值
     * @param file
     */
    private void contrast(File file) {}

    /**
     * 下载文件
     * @param map
     */
    private void loadingData(Map map) {}

    /**
     * 获取文件数据曲线的切点，并将切点信息以数组的形式封装成Map到返回队列中去
     * @author zero
     * @param map 内含加载了数据的实体类File
     */
    private void getTangent(Map map) {}

    /**
     * 获取实验报告，并将生成的实验报告文件以流的形式返回到map队列中去
     * @author zero
     * @param map 内含file:File实验数据文件；url:String，echarts图像地址
     */
    private void getReport(Map map) {}

    /**
     * 保存文件，并将添加成功与否的状态值放入到map队列中去
     * @author zero
     * @param map 内含参数：
     *      username：int，账号；
     *      type：int,文件类型：0CV,1PDV,2SWV,3LSV；
     *      fileName：String,文件名；
     *      xDataArr[]：double[],x轴数据组；
     *      yDataArr[]：double[]，y轴数据组
     */
    private void saveFile(Map map) {}

}
