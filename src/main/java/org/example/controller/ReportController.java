package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/report/empJobData")
    public Result empJobData(){
        log.info("统计各职位员工的数量");
        JobOption empJobData = reportService.getEmpJobData();
        return Result.success(empJobData);
    }

    @GetMapping("/report/empGenderData")
    public Result empGenderData(){
        log.info("统计员工性别");
        List<Map> map = reportService.getEmpGenderData();
        return Result.success(map);
    }
}
