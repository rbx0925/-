package com.atguigu.service;

import com.atguigu.entity.Community;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
public interface CommunityService extends BaseService<Community> {

    List<Community> findAll();
}
