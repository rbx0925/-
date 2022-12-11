package com.atguigu.dao;

import com.atguigu.entity.UserInfo;

/**
 * @Author chenxin
 * @date 2022/12/2
 * @Version 1.0
 */
public interface UserInfoDao extends BaseDao<UserInfo>{
    UserInfo findUserInfoByPhone(String phone);
}
