package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
@DubboService
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    public BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        Integer pageNum= CastUtil.castInt(filters.get("pageNum"),1);
        Integer pageSize= CastUtil.castInt(filters.get("pageSize"),3);
        PageHelper.startPage(pageNum,pageSize);//这行代码一定要在查询之前，设置
        //要进行查询了，但是没有dao的对象
        List<Community> list = getEntityDao().findPage(filters);

        for (Community community : list) {
            Long areaId = community.getAreaId();
            Long plateId = community.getPlateId();

            //根据id找name
            Dict areaDict = dictDao.getById(areaId);
            Dict plateDict = dictDao.getById(plateId);

            community.setAreaName(areaDict.getName());
            community.setPlateName(plateDict.getName());
        }
        //到此处，List集合中所有的community对象都有区域的名字和板块的名字
        return new PageInfo<>(list,3);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        community.setAreaName(dictDao.getById(community.getAreaId()).getName());
        community.setPlateName(dictDao.getById(community.getPlateId()).getName());
        return community;
    }
}
