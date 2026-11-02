package org.example.service;

import org.example.pojo.ClazzOption;
import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

public interface StudentService {
    public PageResult getAllStudent(StudentQueryParam studentQueryParam);

    void deleteStudentByIds(List<Integer> ids);

    void addStudent(Student student);

    Student getStudentById(Integer id);

    void updateStudent(Student student);

    void updateStudentViolation(Integer id, Integer score);

    List<Map> getDegreeData();

    ClazzOption getStudentData();
}
