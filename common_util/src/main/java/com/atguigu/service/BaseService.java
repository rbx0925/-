package com.atguigu.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
public interface BaseService<T> {

    PageInfo<T> findPage(Map<String,Object> filters);

    void insert(T t);

    T getById(Serializable id);

    void update(T t);

    void delete(Serializable id);
}
