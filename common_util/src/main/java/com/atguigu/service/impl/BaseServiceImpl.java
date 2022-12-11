package com.atguigu.service.impl;

import com.atguigu.dao.BaseDao;
import com.atguigu.service.BaseService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //在这个位置创建一个抽象方法
        //通过这个方法获取实际的dao层对象
    public abstract BaseDao<T> getEntityDao();

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {
        Integer pageNum= CastUtil.castInt(filters.get("pageNum"),1);
        Integer pageSize= CastUtil.castInt(filters.get("pageSize"),3);
        PageHelper.startPage(pageNum,pageSize);//这行代码一定要在查询之前，设置
        //要进行查询了，但是没有dao的对象
        List<T> list = getEntityDao().findPage(filters);
        return new PageInfo<>(list,3);
    }

    @Override
    public void insert(T t) {
        getEntityDao().insert(t);
    }

    @Override
    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    @Override
    public void update(T t) {
        getEntityDao().update(t);
    }

    @Override
    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }
}
