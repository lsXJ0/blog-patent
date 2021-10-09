package com.example.boot.service;

import com.example.boot.dao.domain.LoginParams;
import com.example.boot.dao.domain.SysUser;
import com.example.boot.dao.vo.LoginUserVo;
import com.example.boot.dao.vo.Result;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 11:17
 */
@Transactional
public interface LoginService {
    //登陆功能
    Result Login(LoginParams loginParams);

    //判断token是否存在
    SysUser checkToken(String token);

    //登出功能
    Result LoginOut(String token);

    //注册功能
    Result register(LoginParams loginParams);
}
