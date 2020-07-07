package com.baokaicong.sm.controller.advice;

import com.baokaicong.sm.bean.Result;
import com.baokaicong.sm.exception.SMException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SMException.class)
    @ResponseBody
    public Result handleBE(SMException e){
        return Result.builder()
                .code(e.getCode())
                .data(e.getMessage())
                .build()
                .generateTime();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleE(Exception e){
        return Result.builder()
                .code("500")
                .data("服务端未知异常")
                .build()
                .generateTime();
    }
}
