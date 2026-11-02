package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Clazz;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClazzMapper {
     List<Clazz> findById(String name, LocalDate begin, LocalDate end) ;

     void deleteById(Integer id);

     void addClazz(Clazz clazz);

     Clazz getClazzById(Integer id);

     void update(Clazz clazz);

     @Select("select * from clazz ")
     List<Clazz> getAllClazz();
}
