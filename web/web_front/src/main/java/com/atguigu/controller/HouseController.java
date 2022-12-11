package com.atguigu.controller;

import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/12/2
 * @Version 1.0
 */
@Controller
@RequestMapping("/house")
@ResponseBody
public class HouseController {

    @DubboReference
    private HouseService houseService;

    @DubboReference
    private CommunityService communityService;

    @DubboReference
    private HouseBrokerService houseBrokerService;

    @DubboReference
    private HouseImageService houseImageService;

    @DubboReference
    private UserFollowService userFollowService;


    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result list(
            @PathVariable Integer pageNum,
            @PathVariable Integer pageSize,
            @RequestBody HouseQueryVo houseQueryVo){
        System.out.println("查询条件："+houseQueryVo);
        //调用业务层处理业务
        PageInfo<HouseVo> page = houseService.findHouseByHouseQueryVo(pageNum, pageSize, houseQueryVo);

        return Result.ok(page);
    }

    @RequestMapping("/info/{houseId}")
    public Result info(@PathVariable Long houseId, HttpSession session){
        //当前房源的信息(内部关于数据字典的信息已完成转化)
        House house = houseService.getById(houseId);
        //当前房源小区的信息(内部关于数据字典的信息已完成转化)
        Community community = communityService.getById(house.getCommunityId());

        List<HouseBroker> houseBrokerList = houseBrokerService.findBrokerByHouseId(houseId);

        List<HouseImage> houseImage1List = houseImageService.findImageByHouseIdAndType(houseId, 1);

        //不登录其实也是可以看详情，所以info是不能过拦截器的
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        Boolean isFollow=false;//应该去判断当前登录人是否关注的此房源，如果关注了设置为true
        if(userInfo!=null){
            UserFollow userFollow = userFollowService.findFollowByUserAndHouse(userInfo.getId(), houseId);
            if(userFollow!=null){
                //说明关注了
                isFollow=true;
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        map.put("isFollow",isFollow);

        return Result.ok(map);
    }
}
