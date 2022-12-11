package com.atguigu.controller;

import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @DubboReference
    private DictService dictService;

    @RequestMapping
    public String index(){
        //只是做一个页面的跳转，类似的模式在书城项目的购物车展示
        return "dict/index";
    }

    //这个请求是异步请求
    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        //该请求才是查询数据的请求
        System.out.println("父级id:"+id);
        //调用业务层进行数据的查询
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        //将znodes变成json数据返回：
        return Result.ok(znodes);
    }

    @RequestMapping("/findListByParentId/{areaId}")
    @ResponseBody
    public Result findListByParentId(@PathVariable Long areaId){
        List<Dict> dictList = dictService.findChileByParentId(areaId);
        return Result.ok(dictList);
    }
}
