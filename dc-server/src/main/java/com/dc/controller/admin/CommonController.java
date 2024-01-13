package com.dc.controller.admin;

import com.dc.constant.MessageConstant;
import com.dc.result.Result;
import com.dc.utils.AwsS3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    @Autowired
    AwsS3Util awsS3Util;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        String url = null;
        try {
            url = awsS3Util.upload("/uploads", file);
            return Result.success(url);
        } catch (IOException e) {
            log.info("Error occurred while upload: {}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
