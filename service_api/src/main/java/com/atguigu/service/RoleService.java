package com.atguigu.service;

import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/25
 * @Version 1.0
 */
public interface RoleService extends BaseService<Role>{
    List<Role> findAll();

    /*
        一个方法就将两个List集合全部查询出来
        返回值需要能存储两个List集合
     */
    Map<String,List<Role>> findRoleByAdminId(Long adminId);

}
