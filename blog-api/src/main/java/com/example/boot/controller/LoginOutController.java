package com.example.boot.controller;

import com.example.boot.dao.vo.Result;
import com.example.boot.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 16:41
 */
@RestController
@RequestMapping("logout")
@Slf4j
public class LoginOutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result LoginOut(@RequestHeader("Authorization") String token){
        log.info("The Token is :{"+token+"}");
        return loginService.LoginOut(token);
    }
}
