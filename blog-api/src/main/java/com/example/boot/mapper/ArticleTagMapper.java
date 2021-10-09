package com.example.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.boot.dao.domain.Article;
import com.example.boot.dao.domain.ArticleTag;
import org.springframework.stereotype.Service;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 23:00
 */
@Service
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
