package com.company.dept.controller;

import com.company.dept.entities.Dept;
import com.company.dept.service.DeptClientService;
import com.company.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: FengJie
 * @Date: 2019/3/2 0002 20:59
 * @Description: web层调用远程服务
 */
@RestController
@RequestMapping("/deptConsumer/dept")
public class DeptConsumerController {

    @Autowired(required=true)
    private DeptClientService deptClientService;


    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/list")
    public Result list(){
            return deptClientService.list();
    }

    /**
     * 根据ID查询部门
     * @param deptNo
     * @return
     */
    @GetMapping("/get/{deptNo}")
    public Result get(@PathVariable("deptNo") Long deptNo){
        return deptClientService.get(deptNo);
    }

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @PostMapping("/insert")
    public Result insert(Dept dept){
        return deptClientService.insert(dept);
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PostMapping("/update")
    public Result update(Dept dept){
        return deptClientService.update(dept);
    }

    /**
     * 删除部门
     * @param deptNo
     * @return
     */
    @PostMapping("/delete")
    public Result delete(Long deptNo){
        return deptClientService.delete(deptNo);
    }


}
