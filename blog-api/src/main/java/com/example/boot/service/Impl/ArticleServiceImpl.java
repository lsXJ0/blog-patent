package com.example.boot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.boot.dao.domain.*;
import com.example.boot.dao.vo.*;
import com.example.boot.mapper.ArticleBodyMapper;
import com.example.boot.mapper.ArticleMapper;
import com.example.boot.mapper.ArticleTagMapper;
import com.example.boot.mapper.CategoryMapper;
import com.example.boot.service.ArticleService;
import com.example.boot.service.SySUserService;
import com.example.boot.service.TagService;
import com.example.boot.service.ThreadService;
import com.example.boot.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 21:51
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SySUserService sysuserService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result ArticleList(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        //多条件查询语句
        IPage<Article> articleIPage=articleMapper.ArticleList(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
       return Result.success(copyList(articleIPage.getRecords(),true,true));
    }
//    public Result ArticleList(PageParams pageParams) {
//        //获取页数和条目数来创建分页条件
//        Page<Article> page=new Page<Article>(pageParams.getPage(),pageParams.getPageSize());
//
//        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
//        //根据category来筛选
//        if (pageParams.getCategoryId() != null) {
//            wrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        //筛选tag
//        List<Long> articleIds=new ArrayList<>();
//        if (pageParams.getTagId()!=null){
//            LambdaQueryWrapper<ArticleTag> queryWrapper=new LambdaQueryWrapper();
//            //根据TagId来查询文章id
//            queryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
//            //获取标签对应的id
//            for (ArticleTag articleTag : articleTags) {
//                articleIds.add(articleTag.getArticleId());
//            }
//            if (articleIds.size()>0){
//                //在articleIds中查询文章
//                wrapper.in(Article::getId,articleIds);
//            }else {
//                //若没有查询到则返回空值
//                return Result.success(null);
//            }
//        }
//        //根据文章权重和更新日期来排序
//        wrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        //用mybatis-plus的selectpage来查询信息
//        Page<Article> articlePage = articleMapper.selectPage(page, wrapper);
//        //获取查询到的Article信息
//        List<Article> records = articlePage.getRecords();
//        //新建一个ArticleVo格式
//        List<ArticleVo> articleVos;
//        //将文章格式转换为JSON的输出格式
//        articleVos=copyList(records,true,true);
//        //将结果输出到页面上
//        return Result.success(articleVos);
//    }

    @Override
    public Result getHotArticle(Integer limit) {
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        //根据阅读数来倒序
        wrapper.orderByDesc(Article::getViewCounts);
        wrapper.last("limit "+limit);
        //只查看作者信息，标签信息和文章标题
        wrapper.select( Article::getId,Article::getTitle);
        List<Article> articles = articleMapper.selectList(wrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result getNewArticle(Integer limit) {
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        //根据生产日期来倒序
        wrapper.orderByDesc(Article::getCreateDate);
        wrapper.last("limit "+limit);
        //只查看作者信息，标签信息和文章标题
        wrapper.select(Article::getId,Article::getTitle);
        List<Article> articles = articleMapper.selectList(wrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archievs> archievs=articleMapper.listArchives();
        return Result.success(archievs);
    }

    @Override
    public Result ViewArticlesById(Long id) {
        //获取文章信息
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo=copy(article,true,true,true,true);
        //更新文章的阅读量
        ThreadService threadService=new ThreadService();
        threadService.UpdateViewAccount(articleMapper,article);
        return Result.success(articleVo);
    }

    @Override
    public Result Publish(ArticleParam articleParam) {
        /**
         * 文章内容：body信息、内容格式、标签、文章分类、作者信息、article信息
         * 1.添加article信息，获取当前用户信息
         * 2.添加category分类
         * 3.根据获取的tag信息添加标签
         * 4.为article的body信息添加内容和bodyid
         */
        SysUser user = UserThreadLocal.get();
        Article article=new Article();
        article.setAuthorId(user.getId());
        article.setViewCounts(0L);
        article.setCreateDate(System.currentTimeMillis());
        article.setBodyId(-1L);
        article.setTitle(articleParam.getTitle());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCommentCounts(0L);
        article.setSummary(articleParam.getSummary());
        article.setWeight(Article.Article_Common);
        articleMapper.insert(article);
        //获取标签集
        List<TagVo> tags = articleParam.getTags();
        if (tags!=null){
            for (TagVo tag : tags) {
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        //将body信息存入到数据库中
        ArticleBody articleBody=new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        this.articleBodyMapper.insert(articleBody);
        //更新body信息
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        //返回值为ArticleId
        Map<String, String> IdMap=new HashMap<>();
        IdMap.put("id",article.getId().toString());
        return Result.success(IdMap);
    }

    /**
     * 将文章格式转换为JSON的输出格式
     * @param articles
     * @return
     */
    private List<ArticleVo> copyList(List<Article> articles,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVos=new ArrayList<>();
        for (Article record : articles) {
            articleVos.add(copy(record,isTag,isAuthor));
        }
        return articleVos;
    }

    /**
     * 将单个Article转换为ArticleVo
     * 添加category信息和body信息
     * @param article
     * @return
     */
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor) {
        //创建一个ArticleVo
        ArticleVo articleVo=new ArticleVo();
        //将Article原有的信息复制到ArticleVo中
        BeanUtils.copyProperties(article,articleVo);
        //判断是否加入作者信息
        if (isAuthor){
            Long authorId = article.getAuthorId();
            SysUser user = sysuserService.getSysUserByAuthorId(authorId);
            if (user.getNickname()==null){
                articleVo.setAuthor("罗修鑫");
            }else {
                articleVo.setAuthor(user.getNickname());
            }
        }
        //判断是否加入标签信息
        if (isTag){
            Long articleId = article.getId();
            List<Tag> tags=tagService.getTagsByArticleId(articleId);
            articleVo.setTags(tags);
        }
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }
    /**
     * 将单个Article转换为ArticleVo
     * @param article
     * @return
     */
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        //创建一个ArticleVo
        ArticleVo articleVo=new ArticleVo();
        //将Article原有的信息复制到ArticleVo中
        BeanUtils.copyProperties(article,articleVo);
        //判断是否加入作者信息
        if (isAuthor){
            Long authorId = article.getAuthorId();
            SysUser user = sysuserService.getSysUserByAuthorId(authorId);
            if (user.getNickname()==null){
                articleVo.setAuthor("罗修鑫");
            }else {
                articleVo.setAuthor(user.getNickname());
            }
        }
        //判断是否加入标签信息
        if (isTag){
            Long articleId = article.getId();
            List<Tag> tags=tagService.getTagsByArticleId(articleId);
            articleVo.setTags(tags);
        }
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //根据文章id的BodyId来获取文章内容
        if (isBody){

            ArticleBody articleBody= articleBodyMapper.getBodyContentById(article.getId());
            ArticleBodyVo articleBodyVo=new ArticleBodyVo(articleBody.getContent());
            articleVo.setBody(articleBodyVo);
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            Category category=categoryMapper.getCategoryById(categoryId);
            CategoryVo categoryVo=new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            articleVo.setCategory(categoryVo);

        }
        return articleVo;
    }
}
