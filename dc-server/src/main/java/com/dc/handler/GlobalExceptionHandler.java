package com.dc.handler;

import com.dc.constant.MessageConstant;
import com.dc.exception.BaseException;
import com.dc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("异常信息：{}", ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String username = ex.getMessage().split(" ")[2];
            String message = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(message);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
