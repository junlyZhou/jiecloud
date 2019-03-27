package com.company.dept.hystrix;

import com.company.dept.entities.Dept;
import com.company.dept.service.DeptClientService;
import com.company.utils.result.Result;
import com.company.utils.result.ResultGenerator;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: FengJie
 * @Date: 2019/3/3 0003 16:39
 * @Description:  客户端在服务端不可用时，进行熔断处理 并获取提示信息而不会挂起耗死服务器
 */
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {

   private static  Result fail =  ResultGenerator.genFailResult("服务好像走丢了~~");


    @Override
    public DeptClientService create(Throwable cause) {
        return new DeptClientService() {
            @Override
            public Result list() {
                return fail;
            }

            @Override
            public Result get(Long deptNo) {
                return fail;
            }

            @Override
            public Result insert(Dept dept) {
                return fail;
            }

            @Override
            public Result update(Dept dept) {
                return fail;
            }

            @Override
            public Result delete(Long deptNo) {
                return fail;
            }
        };
    }
}
