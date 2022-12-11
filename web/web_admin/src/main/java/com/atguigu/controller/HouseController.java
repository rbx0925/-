package com.atguigu.controller;

import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/11/29
 * @Version 1.0
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @DubboReference
    private CommunityService communityService;

    @DubboReference
    private DictService dictService;

    @DubboReference
    private HouseService houseService;

    @DubboReference
    private HouseImageService houseImageService;

    @DubboReference
    private HouseBrokerService houseBrokerService;

    @DubboReference
    private HouseUserService houseUserService;

    @RequestMapping
    public String index(HttpServletRequest request,Map map){
        getSource(map);
        //当前页的房源数据
        Map<String, Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);

        map.put("filters",filters);
        map.put("page",page);
        return "house/index";
    }

    @RequestMapping("/create")
    public String create(Map map){
        getSource(map);
        return "house/create";
    }

    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/success";
    }

    @RequestMapping("/edit/{houseId}")
    public String edit(@PathVariable Long houseId,Map map){
        getSource(map);
        House house = houseService.getById(houseId);
        map.put("house",house);
        return "house/edit";
    }

    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/success";
    }

    @RequestMapping("/delete/{houseId}")
    public String delete(@PathVariable Long houseId){
        houseService.delete(houseId);
        return "redirect:/house";
    }

    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable Long houseId,@PathVariable Integer status){
        houseService.publish(houseId,status);
        return "redirect:/house";
    }

    public void getSource(Map map){
        //需要所有的小区
        List<Community> communityList = communityService.findAll();
        //所有的户型
        List<Dict> houseTypeList = dictService.findChildByCode("houseType");
        //所有的装修情况
        List<Dict> decorationList = dictService.findChildByCode("decoration");
        //所有的楼层
        List<Dict> floorList = dictService.findChildByCode("floor");
        //所有的朝向
        List<Dict> directionList = dictService.findChildByCode("direction");
        //所有的建筑结构
        List<Dict> buildStructureList = dictService.findChildByCode("buildStructure");
        //所有的房屋用途
        List<Dict> houseUseList = dictService.findChildByCode("houseUse");

        map.put("communityList",communityList);
        map.put("houseTypeList",houseTypeList);
        map.put("decorationList",decorationList);
        map.put("floorList",floorList);
        map.put("directionList",directionList);
        map.put("buildStructureList",buildStructureList);
        map.put("houseUseList",houseUseList);
    }


    @RequestMapping("/show/{houseId}")
    public String show(@PathVariable Long houseId,Map map){
        //1. 需要房源的详细信息
        House house = houseService.getById(houseId);//带数据字典中名字的house对象
        //2. 房源所在小区的详细信息
        Community community = communityService.getById(house.getCommunityId());//带数据字典中名字的community对象
        //3. 房源的房源图片(户型图、卧室、客厅、厨房、卫生间的图片，放在前台给客户浏览的)
            //根据房源id和type=1的条件去查询，HouseImageService去查询
        List<HouseImage> houseImage1List = houseImageService.findImageByHouseIdAndType(houseId, 1);
        //4. 房源的房产图片(房产证、合同，不给客户看，中介作为留存)
            //根据房源id和type=2的条件去查询，HouseImageService去查询
        List<HouseImage> houseImage2List = houseImageService.findImageByHouseIdAndType(houseId, 2);
        //5. 房源的经纪人的信息
            //根据房源的id进行查询，HouseBrokerService去查询
        List<HouseBroker> houseBrokerList = houseBrokerService.findBrokerByHouseId(houseId);
        //6. 房源的房东信息
            //根据房源id进行查询，HouseUserService去查询
        List<HouseUser> houseUserList = houseUserService.findUserByHouseId(houseId);
        map.put("house",house);
        map.put("community",community);
        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);
        return "house/show";
    }
















}
