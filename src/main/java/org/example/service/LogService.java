package org.example.service;

import org.example.pojo.OperateLog;
import org.example.pojo.PageResult;
import org.example.pojo.Result;

import java.util.List;

public interface LogService {

    public PageResult getLog(Integer page, Integer pageSize);
}
