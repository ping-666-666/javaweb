package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    public List<Student> getStudent(String name, Integer degree, Integer clazzId) ;

    void deleteStudentByIds(List<Integer> ids);

    void insert(Student student);

    Student getById(Integer id);

    void update(Student student);
    @Update("update student set violation_score = #{violationScore} ,violation_count =#{violationCount} where id =#{id} ")
    void updateViolation(Student student);

    List<Map> getDegree();

    List<Map> getStudentData();
}
