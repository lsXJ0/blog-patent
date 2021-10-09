package com.example.boot.dao.vo;

import lombok.Data;

/**
 * @author Luo_xiuxin
 * @create 2021-10-01 21:53
 */
@Data
public class PageParams {
    //当前页的条数
    private Integer PageSize;
    //当前页数
    private Integer Page;
    //文章分类
    private Long categoryId;
    //标签分类
    private Long tagId;

    private String year;
    //按年月分页
    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
