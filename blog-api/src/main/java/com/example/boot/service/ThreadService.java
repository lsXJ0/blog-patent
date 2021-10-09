package com.example.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.boot.dao.domain.Article;
import com.example.boot.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 9:55
 */
@Component
public class ThreadService {

    @Async("taskExecutor")
    public void UpdateViewAccount(ArticleMapper articleMapper, Article article){

        Article UpdateArticle =new Article();
        //获取文章阅读量后加一
        UpdateArticle.setViewCounts(article.getViewCounts()+1);
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId ,article.getId());
        wrapper.eq(Article::getViewCounts,article.getViewCounts());
        //更新文章的阅读量
        articleMapper.update(UpdateArticle,wrapper);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Async("taskExecutor")
    public void UpdateCommentAccount(ArticleMapper articleMapper, Article article){

        Article UpdateArticle =new Article();
        //获取文章评论数之后加上1
        UpdateArticle.setCommentCounts(article.getCommentCounts()+1L);
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId ,article.getId());
        wrapper.eq(Article::getCommentCounts,article.getCommentCounts());
        //更新文章的评论数
        int update = articleMapper.update(UpdateArticle, wrapper);
        System.out.println(update);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
