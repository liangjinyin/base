package com.kaiwen.base.common.configuration.exception;

/**
 * 自定义异常处理
 *
 * @author maofs
 * @version 1.0
 * @date 2019 -12-19 16:28:36
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 693152650236877533L;
    
    private final String msg;
    
    /**
     * Instantiates a new Custom exception.
     *
     * @param msg the msg
     */
    public CustomException(String msg) {
        this.msg = msg;
    }
    
    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return this.msg;
    }
}
