package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.mapper.EmpMapper;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.example.service.impl.EmpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;
//
//    @GetMapping("/emps")
//    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name, Integer gender ,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin ,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
//
//        log.info("查询员工信息，page:{},pageSize:{},name:{},gender:{},begin:{},end:{}", page, pageSize,name, gender, begin, end);
//        PageResult pageResult = empService.findAll(page, pageSize,name, gender, begin, end);
//        return Result.success(pageResult);
//
//
//    }
@GetMapping("/emps")
public Result page(EmpQueryParam empQueryParam) {

    log.info("查询员工信息，EmpQueryParam:{}",empQueryParam);
    PageResult pageResult = empService.findAll(empQueryParam);
    return Result.success(pageResult);


}

@PostMapping("/emps")
    public Result save(@RequestBody Emp emp) throws Exception {
    empService.save(emp);
    return Result.success();


}

@GetMapping("/emps/{id}")
    public Result getinfu(@PathVariable Integer id){
    log.info("查询iD员工的详细信息");
    Emp emp = empService.getinfo(id);
    return Result.success(emp);


}
@PutMapping("/emps")
    public Result update(@RequestBody Emp emp){

    log.info("修改员工信息{}",emp);
    empService.update(emp);
    return Result.success();


}

@DeleteMapping("/emps")
    public Result delete(@RequestParam List<Integer> ids){
    log.info("删除员工{}",ids);
    empService.deleteByIds(ids);
    return Result.success();
}

@GetMapping("/emps/list")
    public Result list(){
    List<Emp> allEmp = empService.findAllEmp();
    return Result.success(allEmp);
}


}
