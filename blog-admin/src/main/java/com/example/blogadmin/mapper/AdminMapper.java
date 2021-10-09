package com.example.blogadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogadmin.dao.domain.Admin;
import com.example.blogadmin.dao.domain.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-06 10:54
 */
@Service
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT * FROM ms_permission  WHERE id IN ( SELECT permission_id FROM ms_admin_permission WHERE admin_id =#{id})")
    List<Permission> FindPermissionByAdminId(Long id);
}
