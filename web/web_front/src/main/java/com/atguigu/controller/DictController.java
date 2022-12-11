package com.atguigu.controller;

import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author chenxin
 * @date 2022/12/2
 * @Version 1.0
 */
@Controller
@RequestMapping("/dict")
@ResponseBody
public class DictController {

    @DubboReference
    private DictService dictService;
    
    @RequestMapping("/findListByDictCode/{code}")
    public Result findListByDictCode(@PathVariable String code){
        List<Dict> dictList = dictService.findChildByCode(code);
        return Result.ok(dictList);
    }

    @RequestMapping("/findListByParentId/{parentId}")
    public Result findListByParentId(@PathVariable Long parentId){
        List<Dict> dictList = dictService.findChileByParentId(parentId);
        return Result.ok(dictList);
    }
    
}
