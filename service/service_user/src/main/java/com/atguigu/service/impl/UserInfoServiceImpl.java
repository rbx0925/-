package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.dao.UserInfoDao;
import com.atguigu.entity.UserInfo;
import com.atguigu.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author chenxin
 * @date 2022/12/2
 * @Version 1.0
 */
@DubboService
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public BaseDao<UserInfo> getEntityDao() {
        return userInfoDao;
    }

    @Override
    public UserInfo findUserInfoByPhone(String phone) {
        return userInfoDao.findUserInfoByPhone(phone);
    }
}
