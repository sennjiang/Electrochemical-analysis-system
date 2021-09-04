package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.pojo.domain.User;

import java.util.List;

/**
 * @description 所有 查询方法 的接口
 * @createDate 2021/8/25-14:36
 */
public interface BaseMapper {

    /**
     * 获取管理员信息列表
     *
     * @param pageStart 页码
     * @param pageSize  每页数据
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
     * @param pageSize 每页数据
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

}
