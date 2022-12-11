package com.atguigu.controller;

import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController{

    @DubboReference
    private CommunityService communityService;

    @DubboReference
    private DictService dictService;

    @RequestMapping
    public String index(HttpServletRequest request,Map map){
        //去业务层查询所有的小区数据(分页+高级查询)
        Map<String, Object> filters = getFilters(request);
        PageInfo<Community> page = communityService.findPage(filters);
        map.put("filters",filters);
        map.put("page",page);
        //将所有的区域信息查询出来，放在请求域(写死默认就是北京的所有区域)
            //去数据字典查询(DictService)
        List<Dict> areaList = dictService.findChildByCode("beijing");
        map.put("areaList",areaList);
        return "community/index";
    }

    @RequestMapping("/create")
    public String create(Map map){
        List<Dict> areaList = dictService.findChildByCode("beijing");
        map.put("areaList",areaList);
        return "community/create";
    }

    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);
        return "common/success";
    }

    @RequestMapping("/edit/{communityId}")
    public String edit(@PathVariable Long communityId,Map map){
        List<Dict> areaList = dictService.findChildByCode("beijing");
        map.put("areaList",areaList);
        Community community = communityService.getById(communityId);
        map.put("community",community);
        return "community/edit";
    }

    @RequestMapping("/update")
    public String update(Community community){
        communityService.update(community);
        return "common/success";
    }

    @RequestMapping("/delete/{communityId}")
    public String delete(@PathVariable Long communityId){
        communityService.delete(communityId);
        return "redirect:/community";
    }
}
