package com.atguigu.service;

import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
public interface HouseService extends BaseService<House>{
    void publish(Long houseId,Integer status);

    //单独写一个适配前台查询的查找方法
    PageInfo<HouseVo> findHouseByHouseQueryVo(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
