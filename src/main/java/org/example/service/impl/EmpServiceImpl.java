package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;

import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

//    @Override
//    public PageResult findAll(Integer page , Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
//       PageHelper.startPage(page, pageSize);
//
//        List<Emp> list =empMapper.findAll(name,  gender,  begin,  end);
//        Page<Emp> p = (Page<Emp>) list;
//        return new PageResult(p.getTotal(),p.getResult());
//    }
//}
@Override
public PageResult findAll(EmpQueryParam empQueryParam) {
    PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());

    List<Emp> list =empMapper.findAll(empQueryParam);
    Page<Emp> p = (Page<Emp>) list;
    return new PageResult(p.getTotal(),p.getResult());
}
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) throws Exception {
    //1.保存员工基本信息
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);


            //2。保存工作经历
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);}
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工" + emp);
            empLogService.insertLog(empLog);
        }

    }

    @Override
    public Emp getinfo(Integer id) {
       return  empMapper.getById(id);
    }

    @Override
    public void update(Emp emp) {
    //   1.修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
    //  2,修改员工工作经历
        //先删除
        empExprMapper.deleteByIds(Arrays.asList(emp.getId()));
        //再添加
        Integer empId = emp.getId();
        if(!CollectionUtils.isEmpty(emp.getExprList())){
           emp.getExprList().forEach(expr -> expr.setEmpId(empId));
           empExprMapper.insertBatch(emp.getExprList());

        }



    }

    @Override
    public void deleteByIds(List<Integer> ids) {
      empMapper.deleteByIds(ids);
      empExprMapper.deleteByIds(ids);

    }

    @Override
    public List<Emp> findAllEmp() {
        EmpMapper empMapper1 = empMapper;
       return empMapper1.getAllEmp();
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp emp1 =  empMapper.getByUsernameAndPassword(emp.getUsername(),emp.getPassword());
        if (emp1 != null) {
           Map<String,Object> map = new HashMap<>();
           map.put("id",emp1.getId());
           map.put("username",emp1.getUsername());
            String jwt = JwtUtil.generateJwt(map);
            LoginInfo loginInfo = new LoginInfo(emp1.getId(),emp1.getUsername(), emp1.getName(), jwt);
            return loginInfo;
        }
        return null;

    }
}
