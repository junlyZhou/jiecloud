package com.company.dept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: FengJie
 * @Date: 2019/2/27 17:58
 * @Description:
 * @EnableEurekaClient 本服务启动后自动注册到 Eureka 注册中心
 * @EnableDiscoveryClient 服务发现
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class DeptProviderApp  {
    public static void main(String[] args) {
        SpringApplication.run(DeptProviderApp.class, args);
    }
}
