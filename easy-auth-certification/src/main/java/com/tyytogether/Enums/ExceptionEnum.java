package com.tyytogether.Enums;

public enum ExceptionEnum {
    // header 手动注入异常
    HEADER_MANUAL_INJECT_EX(10000, "警告！Header被注入登陆信息"),
    NO_TOKEN_KEY(10001, "警告！Header缺少配置文件中${header-token-key}对应key");

    private Integer code;
    private String msg;

    ExceptionEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
