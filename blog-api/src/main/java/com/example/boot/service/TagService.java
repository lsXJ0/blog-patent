package com.example.boot.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.dao.domain.Tag;
import com.example.boot.dao.vo.Result;
import com.example.boot.dao.vo.TagVo;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:50
 */
public interface TagService{
    List<Tag> getTagsByArticleId(Long articleId);

    Result getHotTags(Integer limit);

    //查找所有的标签
    Result findAllTags();

    //根据标签Id分类文章
    Result findDetailById(Long id);
}
