package com.bluedot.electrochemistry.service.role;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public abstract class RoleModularity {

    /**
     * 添加角色 并为其赋予权限
     */
    protected abstract void addRole();

    /**
     * 删除角色
     */
    protected abstract void deleteRole();
}
