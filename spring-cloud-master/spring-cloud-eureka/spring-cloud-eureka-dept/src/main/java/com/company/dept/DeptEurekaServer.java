package com.company.dept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: FengJie
 * @Date: 2019/3/1 16:14
 * @Description: EurekaServer服务器端启动类,接受其它微服务注册进来
 */
@SpringBootApplication
@EnableEurekaServer
public class DeptEurekaServer  {
    public static void main(String[] args) {
        SpringApplication.run(DeptEurekaServer.class, args);
    }
}
