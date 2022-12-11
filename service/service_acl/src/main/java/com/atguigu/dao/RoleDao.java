package com.atguigu.dao;

import com.atguigu.entity.Role;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/25
 * @Version 1.0
 */
public interface RoleDao extends BaseDao<Role> {
    //最开始的时候，没有分页，咱们写的就是角色的查询所有，有了分页需求，这个方法就没用了
    List<Role> findAll();

}
