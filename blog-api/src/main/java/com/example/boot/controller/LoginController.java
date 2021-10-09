package com.example.boot.controller;

import com.example.boot.dao.domain.LoginParams;
import com.example.boot.dao.vo.Result;
import com.example.boot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 11:10
 */
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result LoginAction(@RequestBody LoginParams loginParams){
        return loginService.Login(loginParams);
    }
}
