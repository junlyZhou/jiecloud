package com.company.dept.controller;

import com.company.utils.result.Result;
import com.company.utils.result.ResultGenerator;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentMap;

/**
 * @Author: Administrator
 * @Date: 2019/3/2 0002 19:07
 * @Description:  微服务信息描述
 */
@RestController
public class DeptInfoController {

    /**
     * 微服务实例ID
     */
    @Value("${eureka.instance.instance-id}")
    private String instanceId;
    /**
     * 服务发现
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/actuator/info")
    public Result deptInfo(){

        return ResultGenerator.genSuccessResult("来了，老弟~~");
    }

    /**
     *
     * @return
     */
    @GetMapping("/discoveryClient")
   public Result discoveryClient(){
        return ResultGenerator.genSuccessResult(discoveryClient.getInstances(instanceId));
    }

    public static void main(String[] args) {
        ConcurrentMap<Object, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
    }
}
