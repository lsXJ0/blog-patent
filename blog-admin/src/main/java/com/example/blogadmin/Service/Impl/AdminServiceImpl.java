package com.example.blogadmin.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogadmin.Service.AdminService;
import com.example.blogadmin.dao.domain.Admin;
import com.example.blogadmin.dao.domain.Permission;
import com.example.blogadmin.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-06 14:59
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin FindAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername,username);
        wrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public List<Permission> FindPermissionByAdminId(Long id) {

        return  adminMapper.FindPermissionByAdminId(id);
    }
}
