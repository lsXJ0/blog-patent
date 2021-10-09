package com.example.boot.service;

import com.example.boot.dao.domain.SysUser;
import com.example.boot.dao.vo.Result;
import com.example.boot.dao.vo.UserVo;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:38
 */
public interface SySUserService {
    SysUser getSysUserByAuthorId(Long authorId);

    //验证用户所在的token是否存在
    Result findUserByToken(String token);

    //将信息存储到数据库中
    void save(SysUser user);

    //查询UserVo
    UserVo getUserVoById(Long Id);

}
