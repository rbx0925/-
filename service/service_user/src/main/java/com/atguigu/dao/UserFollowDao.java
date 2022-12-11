package com.atguigu.dao;

import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
public interface UserFollowDao extends BaseDao<UserFollow> {

    UserFollow findFollowByUserAndHouse(Long userId,Long houseId);

    List<UserFollowVo> findUserFollow(Long userId);
}
