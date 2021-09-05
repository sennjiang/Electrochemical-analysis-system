package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.pojo.domain.*;

import java.sql.Timestamp;
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
     * @Return List<User> 用户列表
     * @Description 返回用户列表
     * @Param [pageSize 页面大小, currentPage 当前页, key]
     * @Date 2021/9/4 18:55
     **/
    List<User> listUser(int pageSize, int currentPage, String key);

    /**
     * @Return User实体类
     * @Description 根据username查询用户
     * @Param [username 用户名]
     * @Date 2021/9/4 18:57
     **/
    User queryUserByUsername(String username);

    /**
     * @Return User实体类
     * @Description 根据email查询用户
     * @Param [email 邮箱]
     * @Date 2021/9/4 18:58
     **/
    User queryUserByEmail(String email);

    /**
     * 获取管理员信息列表
     *
     * @param pageStart 页码
     * @param pageSize  每页大小
     * @return 管理员列表
     */

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

    /**
     * 比较hash值
     * @param File_hash
     * @param username
     * @return
     */
    long contrastFile(String File_hash,int username);

    /**
     * 按照条件查询
     * @param username 账号
     * @param condition 条件
     * @param timeOrder  时间
     * @param status 0 正常 1 已删除
     * @param type 0 用户 1 系统
     * @param pageStart 页面
     * @param pageSize 页面大小
     * @return List
     */
    List<File> listFileByQueryCondition(int username, String condition, Timestamp timeOrder,
                                        int status,int type, int pageStart, int pageSize);

    /**
     * 查询查询条数
     * @param username 账号
     * @param condition 条件
     * @param timeOrder 时间
     * @param status 0 正常 1 已删除
     * @param type  0 用户 1 系统
     * @return List
     */
    long getFileCountByQuery(int username, String condition, Timestamp timeOrder,
                             int status,int type);

    /**
     * 根据文件id查找对应的文件
     * @author zero
     * @param id 文件的id
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 被查找到的指定文件
     */
    File getFileById(int id,int pageStart,int pageSize);

    /**
     * 电化学分析模块查询文件
     * @author zero
     * @param username 查询指定账户的文件
     * @param queryCondition 查询条件
     * @param queryValue 查询结果
     * @param fileType 0 CV，1 PDV，2 SWV，3 LSV
     * @param timeOrder 0按时间降序，1按时间升序
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 文件列表
     */
    List<File> getFilesByQueryCondition(int username, String queryCondition, String queryValue, int fileType, int timeOrder, int pageStart, int pageSize);
    long getFilesCountByQueryCondition(int username, String queryCondition, int fileType, int timeOrder);

    /**
     * 获取所有算法集合
     * @author zero
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法列表
     */
    List<Algorithm> getAlgorithms(int pageStart, int pageSize);
    long getAlgorithmsCount();

    /**
     * @author zero
     * @param queryCondition 关键字
     * @param type 算法类型 0滤波处理，1平滑处理，2 CV, 3 DPV, 4 SWV, 5 LSV
     * @param timeOrder 0按时间降序，1按时间升序
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法列表
     */
    List<Algorithm> getAlgorithmsByQueryCondition(String queryCondition,int type,int timeOrder,int pageStart, int pageSize);
    long getAlgorithmsCountByQueryCondition(String queryCondition,int type,int timeOrder);

    /**
     * 获取所有算法申请集合
     * @author zero
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法申请列表
     */
    List<AlgorithmSend> getAlgorithmSends(int pageStart, int pageSize);
    long getAlgorithmSendsCount();

    /**
     * @author zero
     * @param queryCondition 关键字
     * @param type 算法类型 0滤波处理，1平滑处理，2 CV, 3 DPV, 4 SWV, 5 LSV
     * @param timeOrder 0按时间降序，1按时间升序
     * @param pageStart 起始页码
     * @param pageSize 一页的数据量
     * @return 算法申请集合
     */
    List<AlgorithmSend> getAlgorithmSendsByQueryCondition(String queryCondition,int type,int timeOrder,int pageStart, int pageSize);
    long getAlgorithmSendsCountByQueryCondition(String queryCondition,int type,int timeOrder);
}
