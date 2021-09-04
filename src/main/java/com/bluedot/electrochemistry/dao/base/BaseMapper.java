package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.pojo.domain.Algorithm;
import com.bluedot.electrochemistry.pojo.domain.AlgorithmSend;
import com.bluedot.electrochemistry.pojo.domain.File;

import java.util.List;

/**
 * @author zero
 * @description 所有 查询方法 的接口
 * @createDate 2021/8/25-14:36
 */
public interface BaseMapper {

    /**
     * 电化学分析模块查询文件
     * @author zero
     * @param username 查询指定账户的文件
     * @param condition 查询的关键字
     * @param fileType 0 CV，1 PDV，2 SWV，3 LSV
     * @param timeOrder 0按时间降序，1按时间升序
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 文件列表
     */
    List<File> listFileByCondition(int username, String condition, int fileType, int timeOrder, int pageStart, int pageSize);
    long getFileCountByCondition(int username, String condition, int fileType, int timeOrder);

    /**
     * 获取所有算法集合
     * @author zero
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法列表
     */
    List<Algorithm> listAlgorithms(int pageStart, int pageSize);
    long getAlgorithmCount();

    /**
     * @author zero
     * @param condition 关键字
     * @param type 算法类型 0滤波处理，1平滑处理，2 CV, 3 DPV, 4 SWV, 5 LSV
     * @param timeOrder 0按时间降序，1按时间升序
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法列表
     */
    List<Algorithm> listAlgorithmsByCondition(String condition,int type,int timeOrder,int pageStart, int pageSize);
    long getAlgorithmCountByCondition(String condition,int type,int timeOrder);

    /**
     * 获取所有算法申请集合
     * @author zero
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法申请列表
     */
    List<AlgorithmSend> listAlgorithmSends(int pageStart, int pageSize);
    long getAlgorithmSendCount();

    /**
     * @author zero
     * @param condition 关键字
     * @param type 算法类型 0滤波处理，1平滑处理，2 CV, 3 DPV, 4 SWV, 5 LSV
     * @param timeOrder 0按时间降序，1按时间升序
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法申请集合
     */
    List<AlgorithmSend> listAlgorithmSendsByCondition(String condition,int type,int timeOrder,int pageStart, int pageSize);
    long getAlgorithmSendCountByCondition(String condition,int type,int timeOrder);
}
