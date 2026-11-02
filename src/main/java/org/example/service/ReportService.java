package org.example.service;

import org.example.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    public JobOption getEmpJobData();

    List<Map> getEmpGenderData();
}
