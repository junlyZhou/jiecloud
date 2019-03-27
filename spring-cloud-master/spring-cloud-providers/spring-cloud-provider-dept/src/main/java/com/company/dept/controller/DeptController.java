package com.company.dept.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.company.dept.entities.Dept;
import com.company.dept.service.IDeptService;
import com.company.utils.result.Result;
import com.company.utils.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 *
 *
 * 前端控制器
 *
 *
 * @author Fengjie
 * @since 2019-03-01
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private IDeptService iDeptService;

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        return ResultGenerator.genSuccessResult(iDeptService.list());
    }

    /**
     * 根据ID查询部门
     * @param deptNo
     * @return
     */
    @GetMapping("/get/{deptNo}")
    public Result get(@PathVariable("deptNo") Long deptNo) {
        return ResultGenerator.genSuccessResult(iDeptService.getById(deptNo));
    }

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody Dept dept){
        if (StrUtil.isEmpty(dept.getDeptName())) {
            return ResultGenerator.genFailResult("部门名称不能为空!");
        }
        if (StrUtil.isEmpty(dept.getDbSource())) {
            return   ResultGenerator.genFailResult("所在库不能为空!");
        }
        return  ResultGenerator.genSuccessResult(iDeptService.save(dept));
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Dept dept) {
        if (ObjectUtil.isNull(dept.getDeptNo())) {
            return ResultGenerator.genFailResult("id不能为空！");
        }
         dept.setUpdateTime(LocalDateTime.now());
        return ResultGenerator.genSuccessResult(iDeptService.updateById(dept));
    }

    /**
     * 删除部门
     * @param deptNo
     * @return
     */
    @PostMapping("/delete")
    public Result  delete(@RequestBody Long deptNo) {
         if (ObjectUtil.isNull(deptNo)) {
             return   ResultGenerator.genFailResult("id不能为空！");
        }
          return   ResultGenerator.genSuccessResult(iDeptService.removeById(deptNo));
    }
}

