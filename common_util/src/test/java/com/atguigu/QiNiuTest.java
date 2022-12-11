package com.atguigu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @Author chenxin
 * @date 2022/11/30
 * @Version 1.0
 * 测试成功，测试代码是可以删除，我是教学，代码我就不删，我把@Test注解删除
 *  在安装的时候，会自动执行单元测试
 */
public class QiNiuTest {
    /**
     * 测试文件上传
     *  现在只是测试，咱们写尚好房，肯定要部署服务器
     */

    public void testUplod(){
        //构造一个带指定 Region 对象的配置类   区域的选择
        Configuration cfg = new Configuration(Region.region2());
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        //java代码，首先需要找到你的七牛云账号：每个账号最少会有一对秘钥(账号的唯一标识)
        String accessKey = "0UZzsk6604MuK4oDmkT_EV875dp4005ubyjegf7-";
        String secretKey = "u-0j6OlTlG6DXn1BrzvB6F1d5nR_ZgZ-5NrhX-TE";
        //还需要指定账号下的空间名称(一个账号有可能有多个空间，你到底上传到哪个空间)
        String bucket = "shfbj0825";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //指定图片的位置
        String localFilePath = "D:\\images\\img014.png";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);//图片到七牛云上的名字(当前图片的唯一标识，删除也是根据这个名字删)
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }


    public void testDelete(){
        //构造一个带指定 Region 对象的配置类   区域也要选择华南
        Configuration cfg = new Configuration(Region.region2());
//...其他参数参考类注释

        //首先找到删除的账号   一对秘钥去锁定
        String accessKey = "0UZzsk6604MuK4oDmkT_EV875dp4005ubyjegf7-";
        String secretKey = "u-0j6OlTlG6DXn1BrzvB6F1d5nR_ZgZ-5NrhX-TE";
        //还需要找到空间的名字
        String bucket = "shfbj0825";
        //图片的名字
        String key = "FmGgq1zqJEOCvqiXdp7GKrdCPKnc";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }
}
