package com.atguigu.dao;

import com.atguigu.entity.RolePermission;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> findPermissionIdByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);


}
