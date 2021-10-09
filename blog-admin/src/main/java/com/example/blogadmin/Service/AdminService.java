package com.example.blogadmin.Service;

import com.example.blogadmin.dao.domain.Admin;
import com.example.blogadmin.dao.domain.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-06 14:58
 */

public interface AdminService {
    /**
     * 查找Admin
     * @param username
     * @return
     */
    Admin FindAdminByUsername(String username);

    /**
     * 根据AdminId来查找Permission
     * @param id
     * @return
     */
    List<Permission> FindPermissionByAdminId(Long id);
}
