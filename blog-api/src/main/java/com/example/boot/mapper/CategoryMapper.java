package com.example.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.dao.domain.Category;
import org.springframework.stereotype.Service;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 23:44
 */
@Service
public interface CategoryMapper extends BaseMapper<Category> {
    Category getCategoryById(Long categoryId);
}
