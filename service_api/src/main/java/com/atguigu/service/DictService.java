package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
public interface DictService {
    /*
        根据parentId查询查询到数据字典中的数据
            返回数据的类型：[{ id:'0331',name:'n3.3.n1',isParent:true},{}]
            [{id:1,name:'全部分类',isParent:true}]
        返回值：List<Dict>  -->  List<Map<String,Object>>
        参数：Long parentId
     */
    List<Map<String,Object>> findZnodes(Long parentId);

    /*
        根据dict_code查询所有的子集
     */
    List<Dict> findChildByCode(String code);

    List<Dict> findChileByParentId(Long parentId);

    Dict getById(Long id);

}
