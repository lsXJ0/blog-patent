package com.example.boot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.boot.dao.domain.Category;
import com.example.boot.dao.vo.CategoryVo;
import com.example.boot.dao.vo.Result;
import com.example.boot.mapper.CategoryMapper;
import com.example.boot.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-03 22:02
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Result findAllCategorys() {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(wrapper);
        List<CategoryVo> categoryVos=new ArrayList<>();
        copy(categories, categoryVos);
        return Result.success(categoryVos);
    }

    //将category复制到categoryvo中
    private void copy(List<Category> categories, List<CategoryVo> categoryVos) {
        for (Category category : categories) {
            CategoryVo categoryVo=new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryVos.add(categoryVo);
        }
    }

    @Override
    public Result categoriesDetailById(Long id) {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Category::getId,id);
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return Result.success(categoryVo);
    }
}
