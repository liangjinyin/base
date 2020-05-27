package com.kaiwen.base.common.configuration.exception;

public enum ResultEnum {
    UNKNOW_ERROR(-1, "unknownMistake"),
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    ERROR(2, "programError"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
