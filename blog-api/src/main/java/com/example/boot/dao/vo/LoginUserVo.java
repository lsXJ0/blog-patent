package com.example.boot.dao.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 15:50
 */
@Data
public class LoginUserVo {

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickname;

    private String avatar;

    private String account;
}
