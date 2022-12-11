package com.atguigu.controller;

import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RolePermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/25
 * @Version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    @DubboReference
    private RoleService roleService;

    @DubboReference
    private PermissionService permissionService;

    @DubboReference
    private RolePermissionService rolePermissionService;

    //点击角色管理和搜索都会发请求到这里，点击搜索有可能有条件，默认是第一页
        //① 从请求中获取请求参数(有可能有，有可能没有)，将请求参数解析到一个map集合内
        //将map集合传到业务层，在传到持久层，持久层通过if判断是否有值，决定是否拼接查询条件
    @RequestMapping
    public String findRole(Map map,HttpServletRequest request){
        //1. 获取请求参数(肯定有pageNum和pageSize,roleName不一定有)
        Map<String, Object> filters = getFilters(request);
        //这个也返回Map集合，value值都是数组，就算只有一个value值也是在数组内，到持久层不好处理
//        Map<String, String[]> parameterMap = request.getParameterMap();

        //1. 调用业务层处理业务,list就是筛选后的结果
        PageInfo<Role> page = roleService.findPage(filters);
        //2. 将数据共享到请求域
        map.put("page",page);
        map.put("filters",filters);//做查询条件的数据回显
        //3. 设置逻辑视图
        return "role/index";
    }

    @RequestMapping("/create")
//    @PreAuthorize("hasAuthority('role.create')")
    public String create(){
        return "role/create";
    }

    @RequestMapping("/save")
//    @PreAuthorize("hasAuthority('role.create')")
    public String save(Role role){
        //1. 获取请求参数(使用pojo作为入参)
        //2. 调用业务层处理业务
        roleService.insert(role);
        //3. 给响应
            //3.1 新增完是跳转到哪里？重定向到查询所有的controller,现在这条路走不通  ×
            //3.2 跳转到一个成功页面
        return "common/success";
    }

    @RequestMapping("/edit/{roleId}")
//    @PreAuthorize("hasAuthority('role.edit')")
    public String edit(@PathVariable Long roleId,Map map){
        //1. 调用业务层进行查询操作
        Role role = roleService.getById(roleId);
        //2. 将查询结果放在请求域
        map.put("role",role);
        //3. 给响应
        return "role/edit";
    }

    @RequestMapping("/update")
//    @PreAuthorize("hasAuthority('role.edit')")
    public String update(Role role){//role对象内有id值，可以根据id修改其他属性信息
        roleService.update(role);
        return "common/success";
    }

    @RequestMapping("/delete/{roleId}")
//    @PreAuthorize("hasAuthority('role.delete')")
    public String delete(@PathVariable Long roleId){
        roleService.delete(roleId);
        //删除完。直接重定向到查询所有，因为没有弹出层！
        return "redirect:/role";
    }

    @RequestMapping("/assignShow/{roleId}")
//    @PreAuthorize("hasAuthority('role.assign')")
    public String assignShow(@PathVariable Long roleId,Map map){
        map.put("roleId",roleId);
        //还得将所有的菜单数据放在请求域->zNodes
            //[{ id:2, pId:0, name:"随意勾选 2", checked:true},{},{}...]
            //List<Map<String,Object>>
        List<Map<String, Object>> zNodes = permissionService.findZNodes(roleId);
        map.put("zNodes",zNodes);
        return "role/assignShow";
    }

    @RequestMapping("/assignPermission")
//    @PreAuthorize("hasAuthority('role.assign')")
    public String assignPermission(Long roleId,Long[] permissionIds){
        rolePermissionService.insertRolePermission(roleId,permissionIds);
        return "common/success";
    }



}
