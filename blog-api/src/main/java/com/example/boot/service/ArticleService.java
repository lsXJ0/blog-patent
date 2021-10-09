package com.example.boot.service;

import com.example.boot.dao.domain.ArticleParam;
import com.example.boot.dao.vo.PageParams;
import com.example.boot.dao.vo.Result;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 21:50
 */

public interface ArticleService {

    /**
     * 获取文章列表
     * @param pageParams
     * @return
     */
    Result ArticleList(PageParams pageParams);

    /**
     * 获取最热文章
     * @return
     */
    Result getHotArticle(Integer limit);

    /**
     * 获取最新文章
     * @return
     */
    Result getNewArticle(Integer limit);

    /**
     * 将文章按时间归档
     * @return
     */
    Result listArchives();

    /**
     * 根据文章id来阅读文章
     * @param id
     * @return
     */
    Result ViewArticlesById(Long id);

    /**
     * 发表文章
     * @param articleParam
     * @return
     */
    Result Publish(ArticleParam articleParam);
}
