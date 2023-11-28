package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.constant.ErrorConstant;
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
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /*
     * @param ex
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/11/28 15:03
     * @description 处理sql异常，用户重名
     **/
    @ExceptionHandler
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String msg = ex.getMessage();
        if (msg.contains("Duplicate entry")) {
            String[] split = msg.split(" ");
            String username = split[2];
            msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
