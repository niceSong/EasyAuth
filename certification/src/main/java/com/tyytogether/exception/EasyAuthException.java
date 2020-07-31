package com.tyytogether.exception;

import com.tyytogether.Enums.ExceptionEnum;

public class EasyAuthException extends RuntimeException{

    private int code;
    private String msg;

    public EasyAuthException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }
}
