package com.atguigu.service;

import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
public interface PermissionService extends BaseService<Permission> {

    List<Map<String,Object>> findZNodes(Long roleId);

    List<Permission> findPermissionByAdminId(Long adminId);

    List<Permission> findAll();

    List<String> findPermissionCodeByAdminId(Long adminId);


}
