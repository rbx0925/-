package com.atguigu.interceptor;

import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author chenxin
 * @date 2022/12/3
 * @Version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证是否处于登录状态
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("userInfo");
        if(userInfo==null){
            //应该跳转至登录页面,重点是这里如何跳转至登录页面？现在是异步请求，
                //如果是同步的话，很简单，直接response重定向到login.html即可
            //应该考虑的是如何给Vue208的响应状态码
            //借助工具返回json对象
            WebUtil.writeJSON(response, Result.build(null, ResultCodeEnum.LOGIN_AUTH));
            return false;
        }
        return true;
    }
}
