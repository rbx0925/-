package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import com.atguigu.util.PermissionHelper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
@DubboService
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Override
    public BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findZNodes(Long roleId) {
        List<Permission> list = permissionDao.findAll();

        //还需要查询出当前角色已拥有的菜单id
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdByRoleId(roleId);

        List<Map<String,Object>> zNodes=new ArrayList<>();
        for (Permission permission : list) {
            Map<String,Object> map=new HashMap<>();
            //{ id:2, pId:0, name:"随意勾选 2", checked:true}
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            //是否选中，并不是所有的都有，只有当前角色拥有的菜单才可以被选中
            if(permissionIdList.contains(permission.getId())){
                map.put("checked",true);
            }

            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long adminId) {
        //如果使用admin账户登录的，就不通过角色再去找权限，直接查询所有权限
        List<Permission> permissionList =null;
        if(adminId==1){
            permissionList = permissionDao.findAll();
        }else{
            permissionList = permissionDao.findPermissionByAdminId(adminId);
        }
        //permissionList所有的菜单信息
        //需要做一个处理，前台是通过thymeleaf渲染，是我们自己写代码，
        // 判断哪个是一级，哪个是二级，这个二级是哪个一级的二级(zTree内部可以识别，相当于使用了框架，不用操心这个事情)
        List<Permission> permissionList1 = PermissionHelper.bulid(permissionList);
        return permissionList1;
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> list = permissionDao.findAll();
        return PermissionHelper.bulid(list);
    }

    @Override
    public void delete(Serializable id) {
        //1.首先判断当前菜单是否有下级
        List<Permission> permissionList = permissionDao.findPermissionByParentId(id);
        //如果有则，都删除
        if(permissionList!=null && permissionList.size()!=0){
            //肯定有下级，就删除下级即可
            for (Permission permission : permissionList) {
                //permission有可能还有下级
                List<Permission> permissionByParentId = permissionDao.findPermissionByParentId(permission.getId());
                if(permissionByParentId!=null && permissionByParentId.size()!=0){
                    //如果有下级，直接删除，最多就三级
                    for (Permission permission1 : permissionByParentId) {
                        permissionDao.delete(permission1.getId());
                    }
                }
                permissionDao.delete(permission.getId());
            }
        }
                //如果下级还有下级，还是要删除(如果级别多，其实可以写成一个递归)
            //如果没有，什么都不做
        //2.把自己本身删除
        permissionDao.delete(id);
    }


    @Override
    public List<String> findPermissionCodeByAdminId(Long adminId) {
        List<String> codes=null;
        if(adminId==1){
            codes=permissionDao.findAllCode();
        }else{
            codes=permissionDao.findPermissionCodeByAdminId(adminId);
        }
        return codes;
    }
}
