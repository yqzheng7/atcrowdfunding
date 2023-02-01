package com.atguigu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {

        // 转至会员登录页
        registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
        // 转至会员注册页
        registry.addViewController("/auth/member/to/reg/page").setViewName("member-reg");
        // 转到会员中心页
        registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
        // 转至会员众筹页
        registry.addViewController("/member/my/crowd").setViewName("member-crowd");

    }
}
