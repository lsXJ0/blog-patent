package com.example.blogadmin.Service;

import com.example.blogadmin.dao.domain.PageParam;
import com.example.blogadmin.dao.domain.Permission;
import com.example.blogadmin.dao.vo.Result;

/**
 * @author Luo_xiuxin
 * @create 2021-10-05 10:05
 */
public interface PermissionService {
    //分页功能
    Result PermissionList(PageParam pageParam);

    //添加
    Result Add(Permission permission);

    //修改
    Result Update(Permission permission);

    //删除
    Result Delete(Long id);
}
