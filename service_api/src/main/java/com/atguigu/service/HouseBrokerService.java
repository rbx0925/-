package com.atguigu.service;

import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findBrokerByHouseId(Long houseId);

    List<Admin> findHouseOtherBroker(Long houseId);
}
