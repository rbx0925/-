package com.atguigu.controller;

import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.HouseBrokerService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    @DubboReference
    private HouseBrokerService houseBrokerService;

    @RequestMapping("/create/{houseId}")
    public String create(@PathVariable Long houseId, Map map){
        map.put("houseId",houseId);
        //需要将除当前房源经纪人以外的其他经纪人
        List<Admin> adminList = houseBrokerService.findHouseOtherBroker(houseId);
        map.put("adminList",adminList);
        return "houseBroker/create";
    }

    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        houseBrokerService.insert(houseBroker);
        return "common/success";
    }

    @RequestMapping("/delete/{houseId}/{houseBrokerId}")
    public String delete(@PathVariable Long houseId,@PathVariable Long houseBrokerId){
        //根据houseBrokerId
        houseBrokerService.delete(houseBrokerId);
        //删除完后去到哪里？去到房源的详情页面
        return "redirect:/house/show/"+houseId;
    }
}
