package com.example.boot.controller;

import com.example.boot.dao.vo.Result;
import com.example.boot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 22:00
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public Result Category(){
        return categoryService.findAllCategorys();
    }

    @GetMapping("detail")
    public Result Details(){
        return categoryService.findAllCategorys();
    }
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }
}
