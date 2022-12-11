package com.atguigu.dao;

import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
public interface HouseImageDao extends BaseDao<HouseImage> {

    List<HouseImage> findImageByHouseIdAndType(Long houseId, Integer type);
}
