package com.auunes.exception;

import com.auunes.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public R<Object> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        return R.businessError(e.getMessage());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public R<Object> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return R.systemError("系统繁忙，请稍后再试");
    }
} 