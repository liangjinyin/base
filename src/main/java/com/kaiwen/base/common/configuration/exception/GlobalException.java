package com.kaiwen.base.common.configuration.exception;


/**
 * 自定义异常
 * 用法：throw new GlobalException(ResultEnum.SUCCESS);
 */
public class GlobalException extends RuntimeException{

    private Integer code;

    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
