package org.example.controller;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.anno.LogOperation;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

//    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping("/depts")
    public Result list() {
        List<Dept> depts = deptService.findAll();
        return Result.success(depts);


    }
    @LogOperation
    @DeleteMapping("/depts")
    public Result delete( Integer id) {
//        System.out.println("删除了部门"+id);
        log.info("删除了部门 {}",id);
        deptService.deleteById(id);
        return Result.success();

    }
    @LogOperation
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept) {
//        System.out.println("新增部门"+dept);
        log.info("新增部门 {}",dept);
        deptService.save(dept);
        return Result.success();

    }

    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id) {
        Dept dept = deptService.getInfo(id);
        return Result.success(dept);
    }

    @LogOperation
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept) {
        deptService.update(dept);
        return Result.success();
    }
}
