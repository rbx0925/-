package com.atguigu.service;

import com.atguigu.entity.RolePermission;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
public interface RolePermissionService extends BaseService<RolePermission> {

    void insertRolePermission(Long roleId,Long[] permissionIds);

}
