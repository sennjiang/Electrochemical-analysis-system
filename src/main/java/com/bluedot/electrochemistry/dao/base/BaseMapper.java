package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.pojo.domain.*;

import java.sql.Timestamp;
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
     * 根据用户名查权限
     *
     */
    List<RoleRight> queryRightByUser(int username);

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

    /**
     * 获取备份文件信息列表
     *
     * @param pageStart 页码
     * @param pageSize  每页大小
     * @return 备份文件列表
     */
    List<File> getBackupFiles(int pageStart, int pageSize);

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
     * @param queryValue     要查询的值
     * @param pageStart      页码
     * @param pageSize       每页大小
     * @return 备份文件列表fi
     */
    List<File> getBackupFileByQueryCondition(String queryCondition, String queryValue, int pageStart, int pageSize);

    /**
     * 根据查询条件获取备份文件数据总数
     *
     * @param queryCondition 查询条件（字段）
     * @param queryValue     要查询的值
     * @return 备份文件数据总数
     */
    Long getBackupFileCountByQueryCondition(String queryCondition, String queryValue);

    /**
     * 获取角色信息列表
     *
     * @param pageStart 页码
     * @param pageSize  每页大小
     * @return 角色列表
     */
    List<Role> getRoles(int pageStart, int pageSize);

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


    /**
     * 比较hash值
     *
     * @param File_hash
     * @param username
     * @return
     */
    long contrastFile(String File_hash, int username);

    /**
     * 按照条件查询
     *
     * @param username  账号
     * @param condition 条件
     * @param timeOrder 时间
     * @param status    0 正常 1 已删除
     * @param type      0 用户 1 系统
     * @param pageStart 页面
     * @param pageSize  页面大小
     * @return List
     */
    List<File> listFileByQueryCondition(int username, String condition, Timestamp timeOrder,
                                        int status, int type, int pageStart, int pageSize);

    /**
     * 查询查询条数
     *
     * @param username  账号
     * @param condition 条件
     * @param timeOrder 时间
     * @param status    0 正常 1 已删除
     * @param type      0 用户 1 系统
     * @return List
     */
    long getFileCountByQuery(int username, String condition, Timestamp timeOrder,
                             int status, int type);

    /**
     * 根据文件id查找对应的文件
     *
     * @param id        文件的id
     * @param pageStart 起始页码
     * @param pageSize  一页的数据量
     * @return 被查找到的指定文件
     * @author zero
     */
    File getFileById(int id, int pageStart, int pageSize);

    /**
     * 电化学分析模块查询文件
     *
     * @param username       查询指定账户的文件
     * @param queryCondition 查询条件
     * @param queryValue     查询结果
     * @param fileType       0 CV，1 PDV，2 SWV，3 LSV
     * @param timeOrder      0按时间降序，1按时间升序
     * @param pageStart      起始页码
     * @param pageSize       一页的数据量
     * @return 文件列表
     * @author zero
     */
    List<File> getFilesByQueryCondition(int username, String queryCondition, String queryValue, int fileType, int timeOrder, int pageStart, int pageSize);

    long getFilesCountByQueryCondition(int username, String queryCondition, int fileType, int timeOrder);

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓算法相关↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 通过算法id查找指定算法
     *
     * @param id 指定算法的id
     * @return 返回一个算法对象
     */
    Algorithm getAlgorithmById(int id);

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

    /**
     * @param queryCondition 关键字
     * @param pageStart      起始页码
     * @param pageSize       一页的数据量
     * @return 算法列表
     * @author zero
     */
    List<Algorithm> getAlgorithmsByQueryCondition(String queryCondition, int pageStart, int pageSize);

    long getAlgorithmsCountByQueryCondition(String queryCondition);

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓算法申请相关↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取所有算法申请集合
     *
     * @param pageStart 起始页码
     * @param pageSize  一页的数据量
     * @return 算法申请列表
     * @author zero
     */
    List<AlgorithmSend> getAlgorithmSends(int pageStart, int pageSize);

    long getAlgorithmSendsCount();

    /**
     * @param queryCondition 关键字
     * @param pageStart      起始页码
     * @param pageSize       一页的数据量
     * @return 算法申请集合
     * @author zero
     */
    List<AlgorithmSend> getAlgorithmSendsByQueryCondition(String queryCondition, int pageStart, int pageSize);

    long getAlgorithmSendsCountByQueryCondition(String queryCondition);

    /**
     * listFiles
     *
     * @param username  username
     * @param type      1 用户文件 2 系统文件
     * @param status    1 正常 2 已移除
     * @param pageStart pageStart
     * @param pageSize  pageSize
     */
    List<File> listFiles(short type, short status, int username, Integer pageStart, Integer pageSize);

    Long countFiles(short type, short status, int username);

    List<Operation> listOperations(Integer type, Integer pageStart, Integer pageSize);

    List<Operation> listOperationsByUser(Integer type, Integer username, Integer pageStart, Integer pageSize);

    Long countOperations(Integer type);

    List<File> searchFileByAdmin(String title0,String title1, short type, int pageStart, Integer pageSize);

    Long countFilesByAdmin(String title0,String title1, short type);

    List<File> searchFileByUser(String title, int status, int username, short type, int pageStart, Integer pageSize);

    Long countFilesByUser(String title, int status, int username, short type);

    List<Operation> searchOperationsByUser(String title, int username, short type, int pageStart, Integer pageSize);

    Long countOperationsBySearchUser(String title, int username, short type);

    List<Operation> searchOperationsByAdmin(String title0,String title1, short type, int pageStart, Integer pageSize);

    Long countOperationsByAdmin(String title0,String title1, short type);

    Long countOperationsByUser( Integer username,Integer type);
}
