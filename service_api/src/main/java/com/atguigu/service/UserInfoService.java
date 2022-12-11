package com.atguigu.service;

import com.atguigu.entity.UserInfo;

/**
 * @Author chenxin
 * @date 2022/12/2
 * @Version 1.0
 */
public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo findUserInfoByPhone(String phone);
}
