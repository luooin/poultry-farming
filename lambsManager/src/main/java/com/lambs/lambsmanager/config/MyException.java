package com.lambs.lambsmanager.config;

/**
 * 自定义异常
 */
public class MyException extends RuntimeException{
    public MyException(String message){
        super(message);
    }
}
