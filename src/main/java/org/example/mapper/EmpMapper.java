package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Emp;
import org.example.pojo.EmpExpr;
import org.example.pojo.EmpQueryParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
//    原始分页
//    @Select("select count(0) from emp;")
//    Long count();
//
//    @Select("select e.*,d.name dept_name from emp e left join dept d on e.dept_id = d.id limit #{start},#{pageSize}")
//    public List<Emp> findAll(Integer start, Integer pageSize);

   // @Select("select e.*,d.name dept_name from emp e left join dept d on e.dept_id = d.id where e.name like concat('%',#{name},'%') and e.gender = #{gender} and e.entry_date between #{begin} and #{end} ")
    List<Emp> findAll(EmpQueryParam empQueryParam);

    /**
     *
     *  新增员工数据
     */
    @Options(useGeneratedKeys = true , keyProperty = "id")
    @Insert("insert into emp (username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);


    Emp getById(Integer id);


    void updateById(Emp emp);

    void deleteByIds(List<Integer> ids);

 /**
  * 统计员工职位数量
  */
    List<Map<String,Object>> countEmpJobData();

    List<Map> countEmpGenderData();

    List<Emp> getAllEmp();

    /**
     * 查询部门下的员工数量
     */
    @Select("select count(0) from emp e where e.dept_id = #{id}")
    Integer getEmpByDeptId(Integer deptId);

    @Select("select id,username,name  from emp where username = #{username} and password = #{password} ")
    Emp getByUsernameAndPassword(String username, String password);
}
