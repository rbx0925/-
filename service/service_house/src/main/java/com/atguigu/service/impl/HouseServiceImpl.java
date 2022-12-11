package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.Dict;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
@DubboService
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;
    @Override
    public BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long houseId, Integer status) {
        House house=new House();
        house.setId(houseId);
        house.setStatus(status);
        houseDao.publish(house);
    }

    @Override
    public PageInfo<HouseVo> findHouseByHouseQueryVo(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        PageHelper.startPage(pageNum,pageSize);
        List<HouseVo> houseVoList = houseDao.findHouseByQueryVo(houseQueryVo);
        for (HouseVo houseVo : houseVoList) {
            //需要将数据字典中三个id值换成三个name值: 需要调用DictDao
            houseVo.setHouseTypeName(dictDao.getById(houseVo.getHouseTypeId()).getName());
            houseVo.setDirectionName(dictDao.getById(houseVo.getDirectionId()).getName());
            houseVo.setFloorName(dictDao.getById(houseVo.getFloorId()).getName());
        }
        return new PageInfo<>(houseVoList,3);
    }

    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        //需要将数据字典中数据的id值换成name值
        house.setHouseTypeName(dictDao.getById(house.getHouseTypeId()).getName());
        house.setFloorName(dictDao.getById(house.getFloorId()).getName());
        house.setBuildStructureName(dictDao.getById(house.getBuildStructureId()).getName());
        house.setDirectionName(dictDao.getById(house.getDirectionId()).getName());
        house.setDecorationName(dictDao.getById(house.getDecorationId()).getName());
        house.setHouseUseName(dictDao.getById(house.getHouseUseId()).getName());
        return house;
    }
}
