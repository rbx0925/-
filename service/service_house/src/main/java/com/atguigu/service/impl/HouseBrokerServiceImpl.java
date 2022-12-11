package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.HouseBrokerDao;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.HouseBrokerService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
@DubboService
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerDao houseBrokerDao;
    @Override
    public BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }

    @Override
    public List<HouseBroker> findBrokerByHouseId(Long houseId) {
        return houseBrokerDao.findBrokerByHouseId(houseId);
    }

    @Override
    public List<Admin> findHouseOtherBroker(Long houseId) {
        //1. 查询到当前房源的所有经纪人信息
        List<HouseBroker> brokerList = houseBrokerDao.findBrokerByHouseId(houseId);
        List<Long> ids=new ArrayList<>();
        for (HouseBroker houseBroker : brokerList) {
            ids.add(houseBroker.getBrokerId());
        }
        //[6,8]
        //2. 查询admin表格，将上面的经纪人排除掉(暂时写在houseBrokerDao)
            //是对admin表格的查询，按理说应该调用AdminDao,但是AdminDao在acl内，无法调用，到微服务架构，
            //service和dao会分开，就可以调用，SOA架构，粒度比较大，容易出现重复问题
       return houseBrokerDao.findHouseOtherBroker(ids);
    }
}
