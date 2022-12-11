package com.atguigu.service;

import com.atguigu.entity.Admin;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
public interface AdminService extends BaseService<Admin>{

    Admin findAdminByUsername(String username);

}
