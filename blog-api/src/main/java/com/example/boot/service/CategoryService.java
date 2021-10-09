package com.example.boot.service;

import com.example.boot.dao.vo.Result;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 22:02
 */
public interface CategoryService {
    Result findAllCategorys();

    Result categoriesDetailById(Long id);
}
