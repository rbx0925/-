package com.atguigu.service;

import com.atguigu.entity.AdminRole;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
public interface AdminRoleService extends BaseService<AdminRole> {
    void insertAdminRole(Long adminId,Long[] roleIds);
}
