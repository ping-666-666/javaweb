package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.example.pojo.Emp;
import org.example.pojo.EmpExpr;
import org.example.pojo.EmpQueryParam;

import java.util.List;

@Mapper
public interface EmpExprMapper {
//    原始分页
//    @Select("select count(0) from emp;")
//    Long count();
//
//    @Select("select e.*,d.name dept_name from emp e left join dept d on e.dept_id = d.id limit #{start},#{pageSize}")
//    public List<Emp> findAll(Integer start, Integer pageSize);

   // @Select("select e.*,d.name dept_name from emp e left join dept d on e.dept_id = d.id where e.name like concat('%',#{name},'%') and e.gender = #{gender} and e.entry_date between #{begin} and #{end} ")
   void insertBatch(List<EmpExpr> exprList);


   void deleteByIds(List<Integer> ids);
}
