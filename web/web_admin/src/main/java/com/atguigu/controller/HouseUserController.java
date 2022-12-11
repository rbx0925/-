package com.atguigu.controller;

import com.atguigu.entity.HouseBroker;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController {

    @DubboReference
    private HouseUserService houseUserService;

    @RequestMapping("/create/{houseId}")
    public String method(@PathVariable Long houseId, Map map){
        map.put("houseId",houseId);
        return "houseUser/create";
    }

    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        houseUserService.insert(houseUser);
        return "common/success";
    }

    @RequestMapping("/edit/{houseUserId}")
    public String edit(@PathVariable Long houseUserId,Map map){
        HouseUser houseUser = houseUserService.getById(houseUserId);
        map.put("houseUser",houseUser);
        return "houseUser/edit";
    }

    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return "common/success";
    }

    @RequestMapping("/delete/{houseId}/{houseUserId}")
    public String method(@PathVariable Long houseId,@PathVariable Long houseUserId){
        houseUserService.delete(houseUserId);
        return "redirect:/house/show/"+houseId;
    }
}
