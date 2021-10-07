package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.pojo.domain.*;

import java.util.List;

import com.bluedot.electrochemistry.pojo.domain.File;

/**
 * @author zero
 * @description 所有 查询方法 的接口
 * @createDate 2021/8/25-14:36
 */
public interface BaseMapper {

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> listUser();

    /**
     * @param pageSize    页面大小
     * @param currentPage 当前页
     * @param key         key
     * @return User实体类
     */
    List<User> listUser(int pageSize, int currentPage, String key);

    /**
     * 根据username查询用户
     *
     * @param username 用户名
     * @return User实体类
     */
    User queryUserByUsername(int username);

    /**
     * 根据email查询用户
     *
     * @param email 邮箱
     * @return User实体类
     */
    User queryUserByEmail(String email);
    /**
     * 获取管理员信息列表
     *
     *
     * @return 管理员列表
     */
    List<User> getAdmins();

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
     * @param queryValue     要查询的值
     * @param pageStart      页码
     * @param pageSize       每页大小
     * @return 管理员列表
     */
    List<User> getAdminByQueryCondition(String queryCondition, String queryValue, int pageStart, int pageSize);

    /**
     * 根据查询条件获取用户数据总数
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue     要查询的值
     * @return 管理员总数
     */
    Long getAdminCountByQueryCondition(String queryCondition, String queryValue);

//    /**
//     * 获取备份文件信息列表
//     *
//     * @param pageStart 页码
//     * @param pageSize  每页大小
//     * @return 备份文件列表
//     */
    List<File> getBackupFiles();

    /**
     * 获取备份文件数据总数
     *
     * @return 备份文件数据总数
     */
    Long getBackupFileCount();

//    /**
//     * 根据查询条件获取备份文件信息列表
//     *
//     * @param queryCondition 查询条件（字段）
//     * @param queryValue     要查询的值
//     * @param pageStart      页码
//     * @param pageSize       每页大小
//     * @return 备份文件列表
//     */
//    List<File> getBackupFileByQueryCondition(String queryCondition, String queryValue, int pageStart, int pageSize);

//    /**
//     * 根据查询条件获取备份文件数据总数
//     *
//     * @param queryCondition 查询条件（字段）
//     * @param queryValue     要查询的值
//     * @return 备份文件数据总数
//     */
//    Long getBackupFileCountByQueryCondition(String queryCondition, String queryValue);

//    /**
//     * 获取角色信息列表
//     *
//     * @param pageStart 页码
//     * @param pageSize  每页大小
//     * @return 角色列表
//     */
    List<Role> getRoles();

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
     * @param queryValue     要查询的值
     * @param pageStart      页码
     * @param pageSize       每页的大小
     * @return 角色信息列表
     */
    List<Role> getRoleByQueryCondition(String queryCondition, String queryValue, int pageStart, int pageSize);

    /**
     * 根据查询条件获取角色数量
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue     要查询的值
     * @return 角色数量
     */
    Long getRoleCountByQueryCondition(String queryCondition, String queryValue);


//    /**
//     * 比较hash值
//     *
//     * @param File_hash
//     * @param username
//     * @return
//     */
//    long contrastFile(String File_hash, int username);

//    /**
//     * 按照条件查询
//     *
//     * @param username  账号
//     * @param condition 条件
//     * @param timeOrder 时间
//     * @param status    0 正常 1 已删除
//     * @param type      0 用户 1 系统
//     * @param pageStart 页面
//     * @param pageSize  页面大小
//     * @return List
//     */
//    List<File> listFileByQueryCondition(int username, String condition, Timestamp timeOrder,
//                                        int status, int type, int pageStart, int pageSize);

//    /**
//     * 查询查询条数
//     *
//     * @param username  账号
//     * @param condition 条件
//     * @param timeOrder 时间
//     * @param status    0 正常 1 已删除
//     * @param type      0 用户 1 系统
//     * @return List
//     */
//    long getFileCountByQuery(int username, String condition, Timestamp timeOrder,
//                             int status, int type);

    /**
     * 根据文件id查找对应的文件
     *
     * @param id        文件的id
     * @return 被查找到的指定文件
     * @author zero
     */
    File getFileById(int id);

//    /**
//     * 电化学分析模块查询文件
//     *
//     * @param username       查询指定账户的文件
//     * @param queryCondition 查询条件
//     * @param queryValue     查询结果
//     * @param fileType       0 CV，1 PDV，2 SWV，3 LSV
//     * @param timeOrder      0按时间降序，1按时间升序
//     * @param pageStart      起始页码
//     * @param pageSize       一页的数据量
//     * @return 文件列表
//     * @author zero
//     */
//    List<File> getFilesByQueryCondition(int username, String queryCondition, String queryValue, int fileType, int timeOrder, int pageStart, int pageSize);

//    long getFilesCountByQueryCondition(int username, String queryCondition, int fileType, int timeOrder);

    /**
     * 获取所有算法集合
     *
     * @param pageStart 起始页码
     * @param pageSize  一页的数据量
     * @return 算法列表
     * @author zero
     */
    List<Algorithm> getAlgorithms(int pageStart, int pageSize);

    long getAlgorithmsCount();

//    /**
//     * @param queryCondition 关键字
//     * @param type           算法类型 0滤波处理，1平滑处理，2 CV, 3 DPV, 4 SWV, 5 LSV
//     * @param timeOrder      0按时间降序，1按时间升序
//     * @param pageStart      起始页码
//     * @param pageSize       一页的数据量
//     * @return 算法列表
//     * @author zero
//     */
//    List<Algorithm> getAlgorithmsByQueryCondition(String queryCondition, int type, int timeOrder, int pageStart, int pageSize);

//    long getAlgorithmsCountByQueryCondition(String queryCondition, int type, int timeOrder)、;

//    /**
//     * 获取所有算法申请集合
//     *
//     * @param pageStart 起始页码
//     * @param pageSize  一页的数据量
//     * @return 算法申请列表
//     * @author zero
//     */
//    List<AlgorithmSend> getAlgorithmSends(int pageStart, int pageSize);

//    long getAlgorithmSendsCount();

//    /**
//     * @param queryCondition 关键字
//     * @param type           算法类型 0滤波处理，1平滑处理，2 CV, 3 DPV, 4 SWV, 5 LSV
//     * @param timeOrder      0按时间降序，1按时间升序
//     * @param pageStart      起始页码
//     * @param pageSize       一页的数据量
//     * @return 算法申请集合
//     * @author zero
//     */
//    List<AlgorithmSend> getAlgorithmSendsByQueryCondition(String queryCondition, int type, int timeOrder, int pageStart, int pageSize);

//    long getAlgorithmSendsCountByQueryCondition(String queryCondition, int type, int timeOrder);

    List<Operation> listOperations(short type);

    /**
     *  listFiles
     * @param username
     * @param type 1 用户文件 2 系统文件
     * @param status 1 正常 2 已移除
     * @param pageStart
     * @param pageSize
     */
    List<File> listFiles(int username, short type, short status, Integer pageStart, Integer pageSize);

}
