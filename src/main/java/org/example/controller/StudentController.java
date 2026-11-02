package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.*;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/students")
    public Result getStudent(StudentQueryParam studentQueryParam){
        log.info(" 查询学生列表 {}" ,studentQueryParam);
        PageResult allStudent = studentService.getAllStudent(studentQueryParam);
        return Result.success(allStudent);

    }

    @DeleteMapping("/students/{ids}")
    public Result deleteStudent(@PathVariable List<Integer> ids){
        log.info("删除学生 {}" , ids);
        studentService.deleteStudentByIds(ids);
        return Result.success();



    }

    @PostMapping("/students")
    public Result addStudent(@RequestBody Student student){
        log.info("增加学生 {}",student);
        studentService.addStudent(student);
        return Result.success();

    }

    @GetMapping({"/students/{id}"})
    public Result getStudentById(@PathVariable Integer id){
        log.info("根据iD查询学生  {}",id);
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    @PutMapping("/students")
    public Result updateStudent(@RequestBody Student student){

        log.info("修改学生{}",student);
        studentService.updateStudent(student);
        return Result.success();
    }

    @PutMapping("/students/violation/{id}/{score}")
    public Result updateStudentViolation(@PathVariable Integer id, @PathVariable Integer score){
        log.info("学生违纪处理{} ,{}",id,score);
      studentService.updateStudentViolation(id,score);
      return Result.success();

    }

    @GetMapping("/report/studentDegreeData")
    public Result getStudentDegreeData(){
        List<Map> degreeData = studentService.getDegreeData();
        return Result.success(degreeData);
    }

    @GetMapping("/report/studentCountData")
    public Result getStudentCountData(){
        ClazzOption clazzOption= studentService.getStudentData();
        return Result.success(clazzOption);
    }

}
