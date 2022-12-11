package com.atguigu.dao;

import com.atguigu.entity.Admin;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
public interface AdminDao extends BaseDao<Admin>{

    Admin findAdminByUsername(String username);
}
