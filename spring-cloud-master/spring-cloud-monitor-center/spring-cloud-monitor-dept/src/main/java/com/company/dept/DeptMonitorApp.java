package com.company.dept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Author: FengJie
 * @Date: 2019/3/4 10:09
 * @Description:  dept服务监控
 *  @EnableHystrixDashboard 开启HystrixDashboard监控
 */

@SpringBootApplication
@EnableHystrixDashboard
public class DeptMonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(DeptMonitorApp.class, args);
    }
}