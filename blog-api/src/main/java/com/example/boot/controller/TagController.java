package com.example.boot.controller;

import com.example.boot.dao.vo.Result;
import com.example.boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 10:24
 */
@RestController
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result HotTags(){
        //获取前三个
        Integer limit=4;
        return tagService.getHotTags(limit);
    }

    @GetMapping
    public Result Tags(){
        return tagService.findAllTags();
    }

    @GetMapping("detail")
    public Result Details(){
        return tagService.findAllTags();
    }
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
