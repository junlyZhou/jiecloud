package com.company.dept.serviceipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.dept.entities.Dept;
import com.company.dept.mapper.DeptMapper;
import com.company.dept.service.IDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * 服务实现类
 *
 *
 * @author Fengjie
 * @since 2019-03-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService{

        }
