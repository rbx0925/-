package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author chenxin
 * @date 2022/12/5
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //配置类可以重写父类的方法
    //Security已经开始工作，未认证的话，去到登录页面(默认提供的)
    //1. 在内存中分配一个临时密码(后期肯定是要做到连接数据库的)

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //对于密码来说，必须要求加密(并且还需要指定加密方式)
            //只处理登录，注册不归SpringSecurity管，必须指定加密方式(注册和登录必须用同一个加密方式)
        auth.inMemoryAuthentication().
                withUser("admin")
                .password(new BCryptPasswordEncoder().encode("root"))
                .roles("");
    }*/

    //将这个方法的返回值对象，装配到ioc容器
        //就是为了注册的时候，可以从ioc容器内获取到这个对象
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
        ① 设置允许iframe进行嵌套
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //调用父级的configure(http),是需要保留的(暂时)
            //要配置SpringSecurity,不需要父级的方法了，我们在自己写
//        super.configure(http);
        //设置允许iframe进行嵌套
        http.headers().frameOptions().disable();

        //设置不需要认证的路径(所有的静态资源、/login)
        http.authorizeRequests()
                .antMatchers("/static/**","/login")
                .permitAll()// 允许匿名访问的路径
                .anyRequest()
                .authenticated();//其他的请求都需要认证
        //设置自己的登录页面
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");

        //设置退出的功能
            //logout功能不用我们写，SpringSecurity实现的
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        //需要将跨域请求关闭
        http.csrf().disable();

        //设置出现无权限异常的处理对象
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }
}
