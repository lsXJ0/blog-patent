package com.example.boot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.boot.dao.domain.Article;
import com.example.boot.dao.domain.Comment;
import com.example.boot.dao.domain.CommentParam;
import com.example.boot.dao.domain.SysUser;
import com.example.boot.dao.vo.CommentVo;
import com.example.boot.dao.vo.Result;
import com.example.boot.dao.vo.UserVo;
import com.example.boot.mapper.ArticleMapper;
import com.example.boot.mapper.CommentMapper;
import com.example.boot.mapper.SySUserMapper;
import com.example.boot.service.CommentService;
import com.example.boot.service.SySUserService;
import com.example.boot.service.ThreadService;
import com.example.boot.utils.UserThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 10:58
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SySUserService sysuserservice;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result getCommentByArticleId(Long id) {
        /**
         * 1.根据id查询评论列表，从comment中查询
         * 2.根据作者id查询作者信息
         * 3.判断level是否为1，并查询它的子评论
         * 4.若为1则查询它的（parent_id）信息
         */

        //获取父评论信息
        LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId,id);
        wrapper.eq(Comment::getLevel,1);
        //第一个评论信息
        List<Comment> comments = commentMapper.selectList(wrapper);
        List<CommentVo> commentVos=copyList(comments);

        return Result.success(commentVos);
    }

    @Override
    public Result ToComment(CommentParam commentParam) {
        /**
         * 1.获取用户信息
         * 2.根据用户信息获取对应的comment信息
         * 3.将comment信息封装进comment中
         *      判断parentid是否为空，是则将parent设置为0
         *      判断comment是否有parentid，有则level为2，没有则level为1
         *      判断Uid是否为空，是则设置为0
         * 4.将comment插入comment表中实现更新
         */
        SysUser user = UserThreadLocal.get();
        Comment comment=new Comment();
        comment.setContent(commentParam.getContent());
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setCreateDate(System.currentTimeMillis());
        if (commentParam.getParent()==null||commentParam.getParent()==0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }
        comment.setParentId(commentParam.getParent()==null?0:commentParam.getParent());
        comment.setToUid(commentParam.getToUserId()==null?0:commentParam.getToUserId());
        commentMapper.insert(comment);
        //增加评论数
        Long articleId = commentParam.getArticleId();
        Article article = articleMapper.selectById(articleId);
        ThreadService threadService=new ThreadService();
        threadService.UpdateCommentAccount(articleMapper,article);
        Long commentCounts = article.getCommentCounts();
        System.out.println(commentCounts);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos=new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //获取作者id
        Long authorId = comment.getAuthorId();
        //根据父评论的作者id查找作者
        UserVo userVo=sysuserservice.getUserVoById(authorId);
        //添加UserVo
        commentVo.setAuthor(userVo);
        //获取子评论
        Integer level = comment.getLevel();
        if (level==1){
            //parentId为子评论的id
            Long parentId = comment.getId();
            //子评论信息
            List<CommentVo> commentVoList=FindCommentByParentId(parentId);
            //循环一遍,子评论的level为2不会进入此循环
            commentVo.setChildrens(commentVoList);
        }
        //获取子评论信息
        if (level>1){
            //获取user信息
            Long toUid = comment.getToUid();
            UserVo uservo= sysuserservice.getUserVoById(toUid);
            commentVo.setToUser(uservo);
        }
        return commentVo;
    }

    private List<CommentVo> FindCommentByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId,parentId);
        wrapper.eq(Comment::getLevel,2);
        List<Comment> comments = commentMapper.selectList(wrapper);
        return copyList(comments);
    }


}
