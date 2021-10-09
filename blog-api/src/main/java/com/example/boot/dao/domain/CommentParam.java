package com.example.boot.dao.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentParam {

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    private String content;

    private Long parent;

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUserId;
}