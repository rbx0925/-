package com.atguigu.controller;

import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chenxin
 * @date 2022/12/2
 * @Version 1.0
 */
@Controller
@RequestMapping("/userInfo")
@ResponseBody
public class UserInfoController {
    @DubboReference
    private UserInfoService userInfoService;

    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone, HttpSession session){
        //真实环境就是一个4位或者6位的随机数
        String code="8888";//如果是真实环境，需要将code发送到用户的手机上，并且将code放在会话域(后续验证验证码是否正确)
                            //现在是模拟，将code响应给前台，还是将验证码放在会话域
        session.setAttribute("code",code);
        return Result.ok(code);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session){
        //1. 获取到注册的数据(code/手机号/密码/昵称)
        String code = registerVo.getCode();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        //2. 在后台继续做一个非空校验
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(phone)||StringUtils.isEmpty(password)||StringUtils.isEmpty(nickName)){
            //直接给前台一个错误响应(参数错误)
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //3. 验证验证码是否正确
        Object trueCode = session.getAttribute("code");
        if(!trueCode.equals(code)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        //4. 手机号是否重复(需要去数据库做查询，根据phone)
        UserInfo userInfo = userInfoService.findUserInfoByPhone(phone);
        if(userInfo!=null){
            //说明查到了，说明重复了
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //5. 将数据保存到数据库即可
        UserInfo info=new UserInfo();
        info.setPhone(phone);
        info.setNickName(nickName);
        info.setPassword(MD5.encrypt(password));//密码加密

        userInfoService.insert(info);

        return Result.ok();
    }


    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpSession session){
        //1. 获取到手机号和密码
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();//password是明文
        //2. 非空校验
        if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(password)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        //3. 验证用户名是否正确(根据phone去查询UserInfo对象)
        UserInfo userInfo = userInfoService.findUserInfoByPhone(phone);
        if(userInfo==null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        //4. 在验证密码是否正确  MD5加密，相同的明文多次加密的结果是一样的
            //还有其他加密方式，相同的明文多次加密的结果不一样，就需要通过解密去验证(后台的登录就不是MD5)
        if(!userInfo.getPassword().equals(MD5.encrypt(password))){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //5. 验证用户是否被锁定(验证status是否是1)
        if(userInfo.getStatus()==0){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //6. 将当前登录人信息，存放在会话域  ★  后期要验证是否处于登录状态，结账之类的都需要当前登录人信息
        session.setAttribute("userInfo",userInfo);

        //7. 需要将用户的信息(手机号、昵称)传到前台，前台要保存到浏览器本地
        Map<String,Object> map=new HashMap<>();
        map.put("phone",phone);
        map.put("nickName",userInfo.getNickName());

        return Result.ok(map);//这里返回200的响应
    }

    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        session.removeAttribute("userInfo");
        return Result.ok();
    }


}
