package com.atguigu.dao;

import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
public interface HouseDao extends BaseDao<House> {
    /**
     * 只有两个属性有值，id 和 status
     * @param house
     */
    void publish(House house);

    List<HouseVo> findHouseByQueryVo(HouseQueryVo houseQueryVo);
}
