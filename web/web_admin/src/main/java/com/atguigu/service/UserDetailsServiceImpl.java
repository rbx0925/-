package com.atguigu.service;

import com.atguigu.entity.Admin;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 * @Component 这个注解必须被Spring扫描到，在ioc容器中创建对象
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @DubboReference
    private AdminService adminService;

    @DubboReference
    private PermissionService permissionService;

    /*
        SpringSecurity在验证用户名和密码的时候，默认调用的是UserDetailsService的loadUserByUsername方法
            我们选择重写，调用的就是重写之后的
        username就是在登录页面输入的用户名
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("用户输入的用户名："+username);
        //1. 通过用户名去数据查询Admin对象回来
        Admin admin = adminService.findAdminByUsername(username);
        //2. 如果对象有值，说明用户名是对的，然后在让SpringSecurity去完成密码的校验(有加密)
        if(admin==null){
            //说明用户名不对
            throw new UsernameNotFoundException("用户错误");
        }
        //本意是检验密码，admin.getPassword()就是从数据库查询回来的密码--> 必须是加密的密码

        //在去校验密码之前，将当前用户的所有权限菜单(只有功能按钮)赋给User对象
        List<String> codeList = permissionService.findPermissionCodeByAdminId(admin.getId());
        List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();

        for (String code : codeList) {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(code);
            grantedAuthorityList.add(simpleGrantedAuthority);
        }
        return new User(username,admin.getPassword(),grantedAuthorityList);
    }
}
