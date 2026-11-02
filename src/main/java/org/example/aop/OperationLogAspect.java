package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.anno.LogOperation;
import org.example.mapper.OprateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    private OprateLogMapper oprateLogMapper;

    @Around("@annotation(olog)")
    public Object around(ProceedingJoinPoint joinPoint, LogOperation olog) throws Throwable{

    long starttime =System.currentTimeMillis();

    Object result = joinPoint.proceed();

    long endtime =System.currentTimeMillis();

    long time =endtime-starttime;

    OperateLog operateLog = new OperateLog();
    operateLog.setOperateEmpId(getCurrentEmpId());
    operateLog.setOperateTime(LocalDateTime.now());
    operateLog.setCostTime(time);
    operateLog.setMethodName(joinPoint.getSignature().getName());
    operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
    operateLog.setReturnValue(result.toString());
    operateLog.setClassName(joinPoint.getTarget().getClass().getName());

    oprateLogMapper.insert(operateLog);
    return result;

    }

    public Integer getCurrentEmpId(){
        return CurrentHolder.getCurrentEmpId();
    }

}
