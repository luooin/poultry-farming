package com.lambs.lambsmanager.config;

import com.lambs.lambsmanager.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 捕获指定类的异常
     * @param me
     * @return
     */
    @ExceptionHandler(MyException.class)
    public R<String> exceptionHandler(MyException me) {
        return R.error(me.getMessage());
    }
}
