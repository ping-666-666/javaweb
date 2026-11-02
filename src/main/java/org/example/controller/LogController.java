package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.OperateLog;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class LogController {
    @Autowired
    private LogService logService ;

    @GetMapping("/log/page")
    public Result getLog(Integer page,Integer pageSize){
        PageResult pageResult = logService.getLog(page, pageSize);
        return Result.success(pageResult);
    }
}
