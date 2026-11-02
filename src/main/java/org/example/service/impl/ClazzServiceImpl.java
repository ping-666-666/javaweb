package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ClazzMapper;
import org.example.pojo.Clazz;
import org.example.pojo.EmpQueryParam2;
import org.example.pojo.PageResult;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult findAll(EmpQueryParam2 empQueryParam2) {
        PageHelper.startPage(empQueryParam2.getPage(), empQueryParam2.getPageSize());
        List<Clazz> byId = clazzMapper.findById(empQueryParam2.getName(), empQueryParam2.getBegin(), empQueryParam2.getEnd());
        Page p = (Page) byId ;
        return new PageResult(p.getTotal(),p.getResult());

    }

    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    @Override
    public void addClaze(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazz.setCreateTime(LocalDateTime.now());
        clazzMapper.addClazz(clazz);

    }

    @Override
    public Clazz getClazzById(Integer id) {
        return clazzMapper.getClazzById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    @Override
    public List<Clazz> getAllClazz() {
       return clazzMapper.getAllClazz();
    }
}
