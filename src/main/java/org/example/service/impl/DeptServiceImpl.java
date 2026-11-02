package org.example.service.impl;

import org.apache.ibatis.annotations.Mapper;
import org.example.exception.BusinessException;
import org.example.mapper.DeptMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Dept;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper  empMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();

    }

    @Override
    public void deleteById(Integer id) {
       if(empMapper.getEmpByDeptId(id)>0){
           throw new BusinessException("对不起，当前部门下有员工，不能直接删除！");
       }else {
           deptMapper.deleteById(id);
       }
    }

    @Override
    public void save(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.save(dept);
    }

    @Override
    public Dept getInfo(Integer id) {
       return deptMapper.getInfo(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
