package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class CrowdZuulMain80 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdZuulMain80.class, args);
    }
}
