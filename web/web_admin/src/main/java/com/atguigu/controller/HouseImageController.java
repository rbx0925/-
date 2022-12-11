package com.atguigu.controller;

import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @DubboReference
    private HouseImageService houseImageService;


    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Integer type, Map map){
        map.put("houseId",houseId);
        map.put("type",type);
        return "house/upload";
    }
    //异步传上来的
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(
            @PathVariable Long houseId,
            @PathVariable Integer type,
            @RequestParam("file") MultipartFile[] files) throws IOException {
        //1. 将图片上传到七牛云服务器
        for (MultipartFile file : files) {
            //file就是需要上传的文件
            String fileName= UUID.randomUUID().toString();//只要不重复即可
            QiniuUtil.upload2Qiniu(file.getBytes(),fileName);

            //2. 需要在数据库内添加记录
            HouseImage houseImage=new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setImageName(fileName);
            houseImage.setImageUrl("http://rm5dtfln6.hn-bkt.clouddn.com/"+fileName);
            houseImage.setType(type);

            houseImageService.insert(houseImage);

        }
        return Result.ok();
    }

    @RequestMapping("/delete/{houseId}/{houseImageId}")
    public String delete(@PathVariable Long houseId,@PathVariable Long houseImageId){
        //1. 从七牛云将图片删除(这一步可以不做)，但是花钱啊
        HouseImage houseImage = houseImageService.getById(houseImageId);
        QiniuUtil.deleteFileFromQiniu(houseImage.getImageName());
        //2. 从数据库将图片信息删除(逻辑删除)
        houseImageService.delete(houseImageId);
        //3. 删除完成后回到详情页面
        return "redirect:/house/show/"+houseId;
    }
}
