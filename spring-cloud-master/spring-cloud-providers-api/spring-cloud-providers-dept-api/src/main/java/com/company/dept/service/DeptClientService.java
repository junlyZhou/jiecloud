package com.company.dept.service;

import com.company.dept.entities.Dept;
import com.company.dept.hystrix.DeptClientServiceFallbackFactory;
import com.company.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: FengJie
 * @Date: 2019/3/2 0002 20:19
 * @Description: 部门客户端接口远程调用
 *
 *  fallbackFactory 进行
 */
@FeignClient(name = "PROVIDER-DEPT-HYSTRIX",fallbackFactory = DeptClientServiceFallbackFactory.class)
@RequestMapping("/dept")
public interface DeptClientService {

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/list")
    public Result list();

    /**
     * 根据ID查询部门
     * @param deptNo
     * @return
     */
    @GetMapping("/get/{deptNo}")
    public Result get(@PathVariable("deptNo") Long deptNo);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @PostMapping("/insert")
    public Result insert(Dept dept);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PostMapping("/update")
    public Result update(Dept dept);

    /**
     * 删除部门
     * @param deptNo
     * @return
     */
    @PostMapping("/delete")
    public Result delete(Long deptNo);

}
