package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.RolePermission;
import com.atguigu.service.RolePermissionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
@DubboService
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Override
    public BaseDao<RolePermission> getEntityDao() {
        return rolePermissionDao;
    }

    @Override
    @Transactional
    public void insertRolePermission(Long roleId, Long[] permissionIds) {
        //1. 根据roleId将目前的关系删除
        rolePermissionDao.deleteByRoleId(roleId);
        //2. 在重新新增
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission=new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionDao.insert(rolePermission);
        }
    }
}
