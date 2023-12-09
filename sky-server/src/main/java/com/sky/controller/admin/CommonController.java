package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.MinIoUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/8 21:21
 * @description 通用接口
 **/
@RestController
@RequestMapping("admin/common")
@Slf4j
@Api("通用接口")
public class CommonController {
    @Autowired
    private MinIoUtil minIoUtil;

    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传: {}", file);
        String url = minIoUtil.upload(file);
        return Result.success(url);
    }
}