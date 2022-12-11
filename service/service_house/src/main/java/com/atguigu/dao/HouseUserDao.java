package com.atguigu.dao;

import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
public interface HouseUserDao extends BaseDao<HouseUser> {
    List<HouseUser> findUserByHouseId(Long houseId);
}
