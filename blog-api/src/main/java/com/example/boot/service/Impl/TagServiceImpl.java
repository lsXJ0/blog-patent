package com.example.boot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.boot.dao.domain.Tag;
import com.example.boot.dao.vo.Result;
import com.example.boot.dao.vo.TagVo;
import com.example.boot.mapper.TagMapper;
import com.example.boot.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 22:51
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagsByArticleId(Long articleId) {
        List<Tag> tags= tagMapper.getTagsByArticleId(articleId);
        return tags;
    }

    @Override
    public Result getHotTags(Integer limit) {
        List<Tag> tags=tagMapper.getHotTags(limit);
        return Result.success(tags);
    }

    @Override
    public Result findAllTags() {
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        List<Tag> tags = tagMapper.selectList(wrapper);
        List<TagVo> tagVos=new ArrayList<>();
        for (Tag tag : tags) {
            TagVo tagVo=new TagVo();
            BeanUtils.copyProperties(tag,tagVo);
            tagVos.add(tagVo);
        }
        return Result.success(tagVos);
    }

    @Override
    public Result findDetailById(Long id) {
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId,id);
        Tag tag = tagMapper.selectById(id);
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return Result.success(tagVo);
    }
}
