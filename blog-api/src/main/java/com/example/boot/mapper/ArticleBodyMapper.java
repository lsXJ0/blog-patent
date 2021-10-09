package com.example.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.dao.domain.ArticleBody;
import com.example.boot.dao.vo.ArticleBodyVo;
import org.springframework.stereotype.Service;

/**
 * @author Luo_xiuxin
 * @create 2021-10-02 23:28
 */
@Service
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
    ArticleBody getBodyContentById(Long Id);
}
