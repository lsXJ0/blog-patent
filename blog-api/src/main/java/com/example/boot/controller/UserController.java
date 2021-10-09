package com.example.boot.controller;

import com.example.boot.dao.vo.Result;
import com.example.boot.service.SySUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 14:59
 */
@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
    @Autowired
    private SySUserService sysuserservice;

    //登陆验证功能
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        log.info(token);
        return sysuserservice.findUserByToken(token);
    }
}
