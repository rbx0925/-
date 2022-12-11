package com.atguigu.dao;

import com.atguigu.entity.AdminRole;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> findRoleIdsByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);


}
