package com.atguigu.dao;

import com.atguigu.entity.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> findPermissionByAdminId(Long adminId);

    List<Permission> findPermissionByParentId(Serializable parentId);

    List<String> findPermissionCodeByAdminId(Long adminId);

    List<String> findAllCode();
}
