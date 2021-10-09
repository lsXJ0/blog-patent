package com.example.boot.dao.vo;

import com.example.boot.dao.domain.Tag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    //防止前端精读损失，将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private Long commentCounts;

    private Long viewCounts;

    private Long weight;
    /**
     * 创建时间
     */
    private String createDate;

    //根据作者id查询author
    private String author;

    //根据文章内容
    private ArticleBodyVo body;

    private List<Tag> tags;

    private List<CategoryVo> categorys;

    private CategoryVo category;

}