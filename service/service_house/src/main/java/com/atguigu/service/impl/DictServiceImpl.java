package com.atguigu.service.impl;

import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
@DubboService
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long parentId) {
        List<Dict> znodes = dictDao.findZnodes(parentId);
        List<Map<String,Object>> result=new ArrayList<>();
        for (Dict znode : znodes) {
            /*[{ id:'0331',name:'n3.3.n1',isParent:true},{}]*/
            Map<String,Object> map=new HashMap<>();
            map.put("id",znode.getId());
            map.put("name",znode.getName());
            map.put("isParent",dictDao.findCountByParentId(znode.getId())>0);//知道当前这个znode是不是父级？

            result.add(map);
        }
        return result;
    }

    @Override
    public List<Dict> findChildByCode(String code) {
        //1. 根据code找到id
        Dict dict = dictDao.findDictByCode(code);
        //2. 在根据id找到它的子集
        return dictDao.findZnodes(dict.getId());
    }

    @Override
    public List<Dict> findChileByParentId(Long parentId) {
        return dictDao.findZnodes(parentId);
    }

    @Override
    public Dict getById(Long id) {
        return dictDao.getById(id);
    }


}
