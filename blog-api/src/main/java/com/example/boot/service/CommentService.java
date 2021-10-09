package com.example.boot.service;

import com.example.boot.dao.domain.CommentParam;
import com.example.boot.dao.vo.Result;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 10:58
 */
public interface CommentService {
    //根据文章id来查询评论内容
    Result getCommentByArticleId(Long id);

    //评论文章
    Result ToComment(CommentParam commentParam);
}
