package com.example.boot.dao.domain;

import com.example.boot.dao.vo.Result;
import lombok.Data;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 11:15
 */
@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;


}
