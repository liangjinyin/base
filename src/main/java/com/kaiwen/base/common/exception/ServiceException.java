package com.kaiwen.base.common.exception;

import com.kaiwen.base.common.enums.ResultCode;
import lombok.Data;

/**
 * @author: liangjinyin
 * @Date: 2018-09-10
 * @Description: 统一处理service层的exception类
 */
@Data
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.createResultCodeByMeg(message).getResultCode();
    }

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getResultMsg());
        this.code = resultCode.getResultCode();
    }
}
