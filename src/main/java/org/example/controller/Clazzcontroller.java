package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Clazz;
import org.example.pojo.EmpQueryParam2;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class Clazzcontroller {
    @Autowired
    private ClazzService clazzService;
//    @GetMapping("/clazzs")
//    public Result getClazz(String name,
//                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
//                           @RequestParam(defaultValue = "1") Integer page,
//                           @RequestParam(defaultValue = "5") Integer pageSize) {
//                log.info("查询班级列表 {} {} {} {} {} ",name,begin,end,page,pageSize);
//        PageResult pageResult = clazzService.findAll(name, begin, end, page, pageSize);
//        return Result.success(pageResult);
//
//
//    }
@GetMapping("/clazzs")
public Result getClazz(EmpQueryParam2 empQueryParam2) {
    log.info("查询班级列表 {} ",empQueryParam2);
    PageResult pageResult = clazzService.findAll(empQueryParam2);
    return Result.success(pageResult);


}

@DeleteMapping("/clazzs/{id}")
    public Result deleteClazz(@PathVariable Integer id) {
    log.info("删除班级{}",id);
    clazzService.deleteById(id);
    return Result.success();
}

@PostMapping("/clazzs")
    public Result addClazz(@RequestBody Clazz clazz) {
    log.info("新增班级{}",clazz);
    clazzService.addClaze(clazz);
    return Result.success();
}

@GetMapping("/clazzs/{id}")
    public Result getClazzById(@PathVariable Integer id) {
    log.info("根据iD查询班级{}",id);
      Clazz clazz = clazzService.getClazzById(id);
      return Result.success(clazz);
}

@PutMapping("/clazzs")
    public Result updateClazz(@RequestBody Clazz clazz) {
    log.info("修改员工{}",clazz);
    clazzService.update(clazz);
    return Result.success();

}

@GetMapping("/clazzs/list")
    public Result getClazzList() {
    log.info("查询所有班级");
    List<Clazz> allClazz = clazzService.getAllClazz();
    return Result.success(allClazz);
}
}
