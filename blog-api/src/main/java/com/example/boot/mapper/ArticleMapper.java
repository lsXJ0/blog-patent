package com.example.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.boot.dao.domain.Archievs;
import com.example.boot.dao.domain.Article;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 21:49
 */
@Service
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archievs> listArchives();

    //多条件查询
    IPage<Article> ArticleList(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
