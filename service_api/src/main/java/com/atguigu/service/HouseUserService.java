package com.atguigu.service;

import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findUserByHouseId(Long houseId);
}
