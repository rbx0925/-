package com.atguigu.dao;

import com.atguigu.entity.Dict;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
public interface DictDao {
    List<Dict> findZnodes(Long parentId);

    Long findCountByParentId(Long parentId);

    Dict findDictByCode(String code);

    Dict getById(Long id);
}
