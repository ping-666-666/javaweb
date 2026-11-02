package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(String name,Integer age ,MultipartFile file) throws Exception {

        log.info("name:{},age:{},file:{}",name,age,file);
        if (!file.isEmpty()) {
//            String originalFilename = file.getOriginalFilename();
//            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
//            String uniqueFilename = UUID.randomUUID().toString().replace("-","")+extName;
            String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
            return Result.success(url);
        }
        return Result.error("上传失败");


    }
}
