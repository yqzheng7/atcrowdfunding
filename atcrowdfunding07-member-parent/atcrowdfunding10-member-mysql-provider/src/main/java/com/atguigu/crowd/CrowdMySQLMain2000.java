package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.crowd.mapper")
public class CrowdMySQLMain2000 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMySQLMain2000.class, args);
    }
}
