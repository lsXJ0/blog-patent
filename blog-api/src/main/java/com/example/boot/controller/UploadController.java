package com.example.boot.controller;

import com.example.boot.dao.vo.Result;
import com.example.boot.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author Luo_xiuxin
 * @create 2021-10-04 9:49
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result Upload(@RequestParam("image") MultipartFile file){
        //获取图片id
        String filename = file.getOriginalFilename();
        //更改名称为随机名称
        String fileName = UUID.randomUUID().toString()
                + "." + StringUtils.substringAfterLast(filename, ".");
        //上传
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            return Result.success(QiniuUtils.url+fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}
