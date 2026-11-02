package org.example.service;

import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.LoginInfo;
import org.example.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

//public interface EmpService {
// PageResult findAll(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
//}
public interface EmpService {
 PageResult findAll(EmpQueryParam empQueryParam);

 void save(Emp emp) throws Exception;

 Emp getinfo(Integer id);

 void update(Emp emp);

 void deleteByIds(List<Integer> ids);

    List<Emp> findAllEmp();

    LoginInfo login(Emp emp);
}
