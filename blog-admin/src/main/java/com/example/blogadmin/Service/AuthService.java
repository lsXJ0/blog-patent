package com.example.blogadmin.Service;

import com.example.blogadmin.dao.domain.Admin;
import com.example.blogadmin.dao.domain.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-06 15:06
 */
@Service
public class AuthService {
    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        //权限认证
        String requestURI = request.getRequestURI();
        //获取权限路径
        Object principal = authentication.getPrincipal();
        if (principal==null||"anonymousUser".equals(principal)){
            //未登录
            return  false;
        }
        UserDetails userDetails= (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.FindAdminByUsername(username);
        if (admin==null){
            //未登录
            return  false;
        }
        Long id = admin.getId();
        if (id==1){
            //超级管理员
            return true;
        }
        //SELECT * FROM `ms_permission`  WHERE id IN
        // ( SELECT permission_id FROM ms_admin_permission WHERE admin_id =1)
        List<Permission> permissionList=adminService.FindPermissionByAdminId(id);
        requestURI= StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return  false;
    }
}
