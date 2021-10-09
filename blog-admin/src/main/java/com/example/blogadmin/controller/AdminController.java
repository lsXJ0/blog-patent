package com.example.blogadmin.controller;

import com.example.blogadmin.Service.PermissionService;
import com.example.blogadmin.dao.domain.PageParam;
import com.example.blogadmin.dao.domain.Permission;
import com.example.blogadmin.dao.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luo_xiuxin
 * @create 2021-10-05 10:01
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permission/permissionList")
    public Result PermissionList(@RequestBody PageParam pageParam){
        return permissionService.PermissionList(pageParam);
    }

    @PostMapping("permission/add")
    public Result PermissionAdd(@RequestBody Permission permission){
        return permissionService.Add(permission);
    }
    @PostMapping("permission/update")
    public Result PermissionUpdate(@RequestBody Permission permission){
        return permissionService.Update(permission);
    }
    @GetMapping("permission/delete/{id}")
    public Result permissionDelete(@PathVariable("id") Long id){
        return permissionService.Delete(id);
    }

}
