package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.pojo.domain.*;

import java.util.List;

import com.bluedot.electrochemistry.pojo.domain.File;

import java.util.List;

/**
 * @author zero
 * @description 所有 查询方法 的接口
 * @createDate 2021/8/25-14:36
 */
public interface BaseMapper {

    /**
     * 获取管理员信息列表
     *
     * @param pageStart 页码
     * @param pageSize  每页大小
     * @return 管理员列表
     */
    List<User> getAdmins(int pageStart, int pageSize);

    /**
     * 获取管理员数据总数
     *
     * @return 管理员总数
     */
    Long getAdminCount();

    /**
     * 根据查询条件获取用户信息列表
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue 要查询的值
     * @param pageStart 页码
     * @param pageSize 每页大小
     * @return 管理员列表
     */
    List<User> getAdminByQueryCondition(String queryCondition , String queryValue , int pageStart , int pageSize);

    /**
     *根据查询条件获取用户数据总数
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue 要查询的值
     * @return 管理员总数
     */
    Long getAdminCountByQueryCondition(String queryCondition , String queryValue);

    /**
     * 获取备份文件信息列表
     *
     * @param pageStart 页码
     * @param pageSize 每页大小
     * @return 备份文件列表
     */
    List<File> getBackupFiles(int pageStart , int pageSize);

    /**
     * 获取备份文件数据总数
     *
     * @return 备份文件数据总数
     */
    Long getBackupFileCount();

    /**
     * 根据查询条件获取备份文件信息列表
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue 要查询的值
     * @param pageStart 页码
     * @param pageSize 每页大小
     * @return 备份文件列表
     */
    List<File> getFileByQueryCondition(String queryCondition , String queryValue , int pageStart , int pageSize);

    /**
     * 根据查询条件获取备份文件数据总数
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue 要查询的值
     * @return 备份文件数据总数
     */
    Long getFileCountByQueryCondition(String queryCondition , String queryValue);

    /**
     * 获取角色信息列表
     *
     * @param pageStart 页码
     * @param pageSize 每页大小
     * @return 角色列表
     */
    List<Role> getRoles(int pageStart , int pageSize);

    /**
     * 获取角色数量
     *
     * @return 角色数量
     */
    Long getRoleCount();

    /**
     * 根据查询条件获取角色信息列表
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue 要查询的值
     * @param pageStart 页码
     * @param pageSize 每页的大小
     * @return 角色信息列表
     */
    List<Role> getRoleByQueryCondition(String queryCondition , String queryValue , int pageStart , int pageSize);

    /**
     * 根据查询条件获取角色数量
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue 要查询的值
     * @return 角色数量
     */
    Long getRoleCountByQueryCondition(String queryCondition , String queryValue);


    List<File> listBinFileBySearch(int username, String condition, int fileType, int timeOrder, int pageStart, int pageSize);
    long getBinFileCountBySearch(int username, String condition, int fileType, int timeOrder);

    /**
     * 根据文件id查找对应的文件
     * @author zero
     * @param id 文件的id
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 被查找到的指定文件
     */
    File findFileById(int id,int pageStart,int pageSize);

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
