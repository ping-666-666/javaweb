package org.example.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    void deleteById(Integer id);
    void save(Dept dept);
    Dept getInfo(Integer id);
    void update(Dept dept);}
