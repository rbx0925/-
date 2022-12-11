package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.DictService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
@DubboService
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @DubboReference
    private DictService dictService;

    @Override
    public BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow=new UserFollow();
        userFollow.setHouseId(houseId);
        userFollow.setUserId(userId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public UserFollow findFollowByUserAndHouse(Long userId, Long houseId) {
        return userFollowDao.findFollowByUserAndHouse(userId,houseId);
    }

    @Override
    public PageInfo<UserFollowVo> findUserFollow(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserFollowVo> userFollowVoList = userFollowDao.findUserFollow(userId);
        for (UserFollowVo userFollowVo : userFollowVoList) {
            //应该调用DictDao的内容，是调用不到的
                //原因是这个模块没有DictDao的内容，他在service_house内
                //1. 将DictDao在写一遍(有重复代码)
                //2. 过一下DictService,service_user是可以调用到service_house中的DictService的
                    //service_user不仅仅是提供者，还是一个消费者(允许的)
            userFollowVo.setHouseTypeName(dictService.getById(userFollowVo.getHouseTypeId()).getName());
            userFollowVo.setFloorName(dictService.getById(userFollowVo.getFloorId()).getName());
            userFollowVo.setDirectionName(dictService.getById(userFollowVo.getDirectionId()).getName());
        }
        return new PageInfo<>(userFollowVoList,3);
    }
}
