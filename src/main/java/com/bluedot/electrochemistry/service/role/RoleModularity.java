package com.bluedot.electrochemistry.service.role;

import com.bluedot.electrochemistry.service.Lifecycle;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public interface RoleModularity extends Lifecycle {

    /**
     * 添加角色 并为其赋予权限
     */
    void addRole();

    /**
     * 删除角色
     */
    void deleteRole();
}
