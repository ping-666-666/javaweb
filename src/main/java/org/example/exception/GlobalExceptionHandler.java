package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("出错了", e);
        return Result.error("操作失败");

    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String substring = message.substring(i);
        String[] s = substring.split(" ");
        return Result.error(s[2]+"重复了");

    }

    @ExceptionHandler
    public Result handleException(BusinessException e) {
        log.info("业务异常：{}",e.getMessage());
        return Result.error(e.getMessage());
    }
}
