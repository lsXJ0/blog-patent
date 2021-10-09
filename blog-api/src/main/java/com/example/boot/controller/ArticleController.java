package com.example.boot.controller;

import com.example.boot.aop.cache.Cache;
import com.example.boot.dao.domain.ArticleParam;
import com.example.boot.dao.vo.PageParams;
import com.example.boot.dao.vo.Result;
import com.example.boot.service.ArticleService;
import io.lettuce.core.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 21:15
 */
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //文章列表
    @PostMapping
    public Result ArticleList(@RequestBody PageParams pageParams){
        return articleService.ArticleList(pageParams);
    }

    //最热文章
    @PostMapping("hot")
    public Result HotArticle(){
        //取前4篇最热文章
        Integer limit=4;
        return articleService.getHotArticle(limit);
    }
    //最新文章
    @PostMapping("new")
    public Result NewArticle(){
        //取前4篇最新文章
        Integer limit=4;
        return articleService.getNewArticle(limit);
    }
    //按时间归档
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    //阅读文章
    @PostMapping("view/{id}")
    public Result viewArticles(@PathVariable("id") Long id){
        return articleService.ViewArticlesById(id);
    }

    @PostMapping("publish")
    public Result Publish(@RequestBody ArticleParam articleParam){
        return articleService.Publish(articleParam);
    }


}
