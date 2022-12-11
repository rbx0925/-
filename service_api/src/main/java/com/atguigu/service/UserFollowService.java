package com.atguigu.service;

import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
public interface UserFollowService extends BaseService<UserFollow> {

    void follow(Long userId,Long houseId);

    UserFollow findFollowByUserAndHouse(Long userId,Long houseId);

    PageInfo<UserFollowVo> findUserFollow(Integer pageNum,Integer pageSize,Long userId);
}
