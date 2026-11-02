package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.StudentMapper;
import org.example.pojo.ClazzOption;
import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public PageResult getAllStudent(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        List<Student> student = studentMapper.getStudent(studentQueryParam.getName(), studentQueryParam.getDegree(), studentQueryParam.getClazzId());
        Page p = (Page) student;
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public void deleteStudentByIds(List<Integer> ids) {
        studentMapper.deleteStudentByIds(ids);
    }

    @Override
    public void addStudent(Student student) {
        studentMapper.insert(student);
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.update(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudentViolation(Integer id, Integer score) {
        Student  student = studentMapper.getById(id);
        student.setViolationCount((short) (student.getViolationCount()+1));
        student.setViolationScore((short) (student.getViolationScore()+score));
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateViolation(student);

    }

    @Override
    public List<Map> getDegreeData() {
        return  studentMapper.getDegree();


    }

    @Override
    public ClazzOption getStudentData() {
        List<Map>list=studentMapper.getStudentData();
        List<Object> clazzList = list.stream().map(datamap -> datamap.get("name")).toList();
        List<Object> dataList = list.stream().map(datamap -> datamap.get("data")).toList();
        return new ClazzOption(clazzList,dataList);
    }
}
