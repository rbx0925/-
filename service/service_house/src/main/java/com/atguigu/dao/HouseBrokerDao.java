package com.atguigu.dao;

import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    List<HouseBroker> findBrokerByHouseId(Long houseId);

    List<Admin> findHouseOtherBroker(List<Long> ids);
}
