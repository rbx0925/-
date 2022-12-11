package com.atguigu.service;

import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
public interface HouseImageService extends BaseService<HouseImage> {

    List<HouseImage> findImageByHouseIdAndType(Long houseId,Integer type);
}
