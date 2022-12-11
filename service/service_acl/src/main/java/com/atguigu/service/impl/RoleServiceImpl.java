package com.atguigu.service.impl;

import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/25
 * @Version 1.0
 */
@DubboService
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, List<Role>> findRoleByAdminId(Long adminId) {
        //1. 查询出所有的角色信息
        List<Role> roleList = roleDao.findAll();
        //2. 查询出当前用户拥有的角色id
        List<Long> roleIds = adminRoleDao.findRoleIdsByAdminId(adminId);
        //3. 循环所有的角色信息
        List<Role> noAssignRoleList=new ArrayList<>();
        List<Role> assignRoleList=new ArrayList<>();
        for (Role role : roleList) {
            //判断role的id是都在roleIds内存在
            if(roleIds.contains(role.getId())){
                //说明role是当前用户已拥有的角色
                assignRoleList.add(role);
            }else{
                //说明role是当前用户未拥有的角色
                noAssignRoleList.add(role);
            }
        }
        Map<String,List<Role>> map=new HashMap<>();
        map.put("noAssignRoleList",noAssignRoleList);
        map.put("assignRoleList",assignRoleList);
        return map;
    }


    @Override
    public BaseDao<Role> getEntityDao() {
        return roleDao;
    }
}
