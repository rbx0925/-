package com.atguigu.controller;

import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
@Controller
@RequestMapping("/userFollow")
@ResponseBody
public class UserFollowController {

    @DubboReference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpSession session){
        //目前先保持手动登录，一会去实现拦截器
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        userFollowService.follow(userInfo.getId(),houseId);
        return Result.ok();
    }

    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable Integer pageNum,@PathVariable Integer pageSize,HttpSession session){
        //查询当前登录人关注的房源信息(带分页)
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        PageInfo<UserFollowVo> userFollowVoList = userFollowService.findUserFollow(pageNum, pageSize, userInfo.getId());
        return Result.ok(userFollowVoList);
    }

    @RequestMapping("/auth/cancelFollow/{userFollowId}")
    public Result cancelFollow(@PathVariable Long userFollowId){
        userFollowService.delete(userFollowId);
        return Result.ok();
    }
}
