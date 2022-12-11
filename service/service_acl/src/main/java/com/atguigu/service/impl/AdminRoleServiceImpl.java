package com.atguigu.service.impl;

import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.service.AdminRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
@DubboService
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {
    @Autowired
    private AdminRoleDao adminRoleDao;
    @Override
    public BaseDao<AdminRole> getEntityDao() {
        return adminRoleDao;
    }

    @Override
    @Transactional
    public void insertAdminRole(Long adminId, Long[] roleIds) {
        //1. 先将adminId的所有角色删除
        adminRoleDao.deleteByAdminId(adminId);
        //2. 在循环添加新的角色信息
        for (Long roleId : roleIds) {
            if(roleId==null)
                continue;
            AdminRole adminRole=new AdminRole();
            adminRole.setRoleId(roleId);
            adminRole.setAdminId(adminId);
            adminRoleDao.insert(adminRole);
        }
    }
}
