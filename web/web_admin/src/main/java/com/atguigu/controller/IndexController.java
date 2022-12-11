package com.atguigu.controller;

import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
@Controller
public class IndexController {

    @DubboReference
    private AdminService adminService;

    @DubboReference
    private PermissionService permissionService;

    @RequestMapping("/")
    public String toIndex(Map map){
        //因为这里要写代码了，要根据当前登录人，查询到它的菜单有哪些，放在请求域内
        //1. 获取到当前登录人的信息(Admin对象)，对于后台来说Admin是我们的用户
        //暂时模拟一个，后期做完登录，直接获取到当前登录人对象即可
        //应该获取到现在登录的admin对象
        /*Long adminId=13L;
        Admin admin = adminService.getById(adminId);*///admin对象就是我模拟的当前登录人对象
        //应该获取到现在登录的admin对象
        //1. 需要获得到Security给我们提供的一个User对象，User对象内有用户名的
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        System.out.println(user);//内部有当前登录人的用户名
        //2. 根据用户名找到Admin对象(admin对象是不是当前登录人的对象呢？100%是的，因为认证通过了)
        Admin admin = adminService.findAdminByUsername(user.getUsername());
        map.put("admin",admin);
        //根据admin信息去寻找他的permission
        List<Permission> permissionList = permissionService.findPermissionByAdminId(admin.getId());
        map.put("permissionList",permissionList);
        return "frame/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "frame/login";
    }

    @RequestMapping("/auth")
    public String auth(){
        return "frame/auth";
    }
}
