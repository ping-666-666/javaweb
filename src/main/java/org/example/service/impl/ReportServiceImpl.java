package org.example.service.impl;

import org.example.mapper.EmpMapper;
import org.example.pojo.JobOption;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        List<String> pos = list.stream().map(dataMap -> (String)dataMap.get("pos")).toList();
        List<Integer> total = list.stream()
                .map(m -> ((Number) m.get("total")).intValue())   // Long -> Integer
                .toList();
        JobOption jobOption = new JobOption(pos,total);

        return jobOption;

    }

    @Override
    public List<Map> getEmpGenderData() {
        List<Map> maps = empMapper.countEmpGenderData();
        return maps;

    }
}
