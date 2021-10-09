package com.example.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogadmin.Service.PermissionService;
import com.example.blogadmin.dao.domain.PageParam;
import com.example.blogadmin.dao.domain.Permission;
import com.example.blogadmin.dao.vo.PageResult;
import com.example.blogadmin.dao.vo.Result;
import com.example.blogadmin.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luo_xiuxin
 * @create 2021-10-05 10:06
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result PermissionList(PageParam pageParam) {
        Page<Permission> page=new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> wrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())){
            wrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, wrapper);
        PageResult<Permission> PageResult=new PageResult<>();
        PageResult.setList(permissionPage.getRecords());
        PageResult.setTotal(permissionPage.getTotal());
        return Result.success(PageResult);
    }

    @Override
    public Result Add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result Update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result Delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
