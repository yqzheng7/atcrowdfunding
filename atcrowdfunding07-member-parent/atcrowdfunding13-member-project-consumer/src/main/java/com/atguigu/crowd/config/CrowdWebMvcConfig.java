package com.atguigu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        // 转至发起众筹的同意协议页
        registry.addViewController("/agree/protocol/page").setViewName("project-agree");

        // 转至发起众筹项目页
        registry.addViewController("/launch/project/page").setViewName("project-launch");

        // 转至项目回报页
        registry.addViewController("/return/info/page").setViewName("project-return");

        // 转至项目确认页
        registry.addViewController("/create/confirm/page").setViewName("project-confirm");

        // 转至项目创建成功页
        registry.addViewController("/create/success").setViewName("project-success");
    }
}
