package com.example.boot.mapper;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:50
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.dao.domain.Tag;
import com.example.boot.dao.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> getTagsByArticleId(Long articleId);

    List<Tag> getHotTags(Integer limit);
}
