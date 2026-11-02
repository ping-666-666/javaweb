package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.OprateLogMapper;
import org.example.pojo.OperateLog;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private OprateLogMapper oprateLogMapper;
    @Override
    public PageResult getLog(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        List<OperateLog> operateLogList = oprateLogMapper.getLog();
        Page<OperateLog> p =(Page<OperateLog>) operateLogList;

        return new PageResult(p.getTotal(),p.getResult());
    }
}
