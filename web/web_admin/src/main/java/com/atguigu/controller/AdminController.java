package com.atguigu.controller;

import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminRoleService;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtil;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @DubboReference
    private AdminService adminService;

    @DubboReference
    private RoleService roleService;

    @DubboReference
    private AdminRoleService adminRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping
    public String findAdmin(HttpServletRequest request,Map map){
        //查询用户的查询条件
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);

        map.put("filters",filters);//做数据回显
        map.put("page",page);//显示本页数据和分页的信息
        return "admin/index";
    }

    @RequestMapping("/create")
    public String create(){
        return "admin/create";
    }

    @RequestMapping("/save")
    public String save(Admin admin){
        //要对admin中的password进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        return "common/success";
    }

    @RequestMapping("/edit/{adminId}")
    public String edit(@PathVariable Long adminId,Map map){
        Admin admin = adminService.getById(adminId);
        map.put("admin",admin);
        return "admin/edit";
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        //刷新页面，查询条件和分页数据都在，所以是当前页
        return "common/success";
    }

    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable Long adminId){
        adminService.delete(adminId);
        //删除的原理是重定向(重定向会重新发请求，没有分页数据和查询数据)，是会回到第一页
        return "redirect:/admin";
    }

    @RequestMapping("/uploadShow/{adminId}")
    public String uploadShow(@PathVariable Long adminId,Map map){
        map.put("adminId",adminId);
        return "admin/upload";
    }

    @RequestMapping("/upload")
    public String upload(Long adminId, @RequestParam("file") MultipartFile file) throws IOException {
        //1. 将图片上传到七牛云
        String fileName= UUID.randomUUID().toString();
        QiniuUtil.upload2Qiniu(file.getBytes(),fileName);
        //2. 对当前用户做修改操作，将head_url进行修改
        Admin admin=new Admin();
        admin.setId(adminId);
        admin.setHeadUrl("http://rm5dtfln6.hn-bkt.clouddn.com/"+fileName);
        adminService.update(admin);
        return "common/success";
    }

    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable Long adminId,Map map){
        map.put("adminId",adminId);
        //需要从数据库查询得到两个List集合
            //1. 当前用户未拥有的角色信息
            //2. 当前用户已拥有的角色信息
        Map<String, List<Role>> map1 = roleService.findRoleByAdminId(adminId);
        //map1中的两个对数据，需要放在请求域(将map1中的数据添加到map内)
        //map.putAll(map1);//将map1中所有数据添加到map中：SE集合学的  如果忘记了获取看

        map.put("assignRoleList",map1.get("assignRoleList"));
        map.put("noAssignRoleList",map1.get("noAssignRoleList"));

        return "admin/assignShow";
    }

    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){//1,2,3,
        System.out.println(adminId);
        System.out.println(Arrays.toString(roleIds));
        adminRoleService.insertAdminRole(adminId,roleIds);
        return "common/success";
    }

}
