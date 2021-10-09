package com.example.boot.dao.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:10
 */
@Data
public class TagVo {
    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String tagName;

    private String avatar;
}
