package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.EmpLog;

@Mapper
public interface EmpLogMapper {
    @Insert("insert into emp_log (operate_time, info) VALUES (#{operateTime},#{info})")
    void insert(EmpLog e);
}
