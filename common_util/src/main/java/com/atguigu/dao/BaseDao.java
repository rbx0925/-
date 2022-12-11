package com.atguigu.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 目标：封装最基本的增删改查
 *  要求：通用
 */
public interface BaseDao<T> {

    List<T> findPage(Map<String,Object> filters);

    void insert(T t);

    T getById(Serializable id);

    void update(T t);

    void delete(Serializable id);
}
