package com.example.boot.controller;

import com.example.boot.dao.domain.CommentParam;
import com.example.boot.dao.vo.Result;
import com.example.boot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 10:51
 */
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //查看评论
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){

        return commentService.getCommentByArticleId(id);
    }
    @PostMapping("create/change")
    public Result ToComment(@RequestBody CommentParam commentParam){

        return commentService.ToComment(commentParam);
    }
}
