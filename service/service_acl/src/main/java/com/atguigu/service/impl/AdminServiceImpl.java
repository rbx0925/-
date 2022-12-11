package com.atguigu.service.impl;

import com.atguigu.dao.AdminDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
@DubboService
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public Admin findAdminByUsername(String username) {
        return adminDao.findAdminByUsername(username);
    }
}
