package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CrowdEurekaMain1000 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdEurekaMain1000.class, args);
    }
}
