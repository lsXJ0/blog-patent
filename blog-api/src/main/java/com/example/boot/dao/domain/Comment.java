package com.example.boot.dao.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class Comment {


    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    private Long createDate;

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUid;

    private Integer level;
}
