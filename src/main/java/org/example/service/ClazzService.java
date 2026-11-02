package org.example.service;

import org.example.pojo.Clazz;
import org.example.pojo.EmpQueryParam2;
import org.example.pojo.PageResult;

import java.util.List;

public interface ClazzService {


    PageResult findAll(EmpQueryParam2 empQueryParam2);

    void deleteById(Integer id);

    void addClaze(Clazz clazz);

    Clazz getClazzById(Integer id);

    void update(Clazz clazz);

    List<Clazz> getAllClazz();
}
