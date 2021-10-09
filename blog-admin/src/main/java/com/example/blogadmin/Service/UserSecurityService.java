package com.example.blogadmin.Service;

import com.example.blogadmin.dao.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Luo_xiuxin
 * @create 2021-10-06 14:56
 */
@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 查找用户
         * 将用户交给security判断密码是否正确
         * 将密码告诉Spring security
         */
        Admin admin=this.adminService.FindAdminByUsername(username);
        if (admin==null){
            return null;
        }
        /**
         * 根据Username获取密码
         */
        UserDetails userDetails=new User(username,admin.getPassword(), new ArrayList<>());
        return userDetails;
    }
}
