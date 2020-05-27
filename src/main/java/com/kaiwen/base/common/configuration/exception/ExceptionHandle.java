package com.kaiwen.base.common.configuration.exception;

import com.kaiwen.base.common.enums.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


/**
 * 统一异常处理
 *
 * @author maofs
 * @version 1.0
 * @date 2019 -12-19 16:28:30
 */
@ControllerAdvice
public class ExceptionHandle {

    /**
     * The constant logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * The Message source handler.
     */
    @Resource
    private MessageSourceHandler messageSourceHandler;

    /**
     * Handle result.
     *
     * @param e the e
     * @return the result
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultCode handle(Exception e) {
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        String flag = "fail";
        //自定义全局异常
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            stringBuilder.append(globalException.getCode()).append(globalException.getMessage());
            //jsr303异常
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            if (!CollectionUtils.isEmpty(constraintViolations)) {

                constraintViolations.forEach(q -> {
                    try {
                        String message = messageSourceHandler.getMessage(q.getMessageTemplate());
                        stringBuilder.append(message);
                    } catch (Exception e1) {
                        stringBuilder.append(dealNoSuchMessageException(e1));
                    }
                });
            }
        } else if (e instanceof CustomException) {
            CustomException ex = (CustomException) e;
            if (!StringUtils.isEmpty(ex.getMsg())) {
                try {
                    String message = messageSourceHandler.getMessage(ex.getMsg());
                    stringBuilder.append(message);
                } catch (Exception e1) {
                    stringBuilder.append(dealNoSuchMessageException(e1));
                }
            } else {
                stringBuilder.append(ex.getMsg());
            }
        } /*else if (e instanceof NotFoundRidException) {
            NotFoundRidException ex = (NotFoundRidException) e;
            stringBuilder.append(ex.getMessage());
        } else if (e instanceof NotFoundFieldNameException) {
            NotFoundFieldNameException ex = (NotFoundFieldNameException) e;
            stringBuilder.append(ex.getMessage());
        } else if (e instanceof FileHandleException) {
            FileHandleException ex = (FileHandleException) e;
            stringBuilder.append(ex.getMessage());
        } else if (e instanceof FileBreakPointRunTimeExcepton) {
            FileBreakPointRunTimeExcepton ex = (FileBreakPointRunTimeExcepton) e;
            stringBuilder.append(ex.getMessage());
        } else if (e instanceof ArgumentNullException) {
            ArgumentNullException ex = (ArgumentNullException) e;
            stringBuilder.append(ex.getMessage());
        } */
        else if (e instanceof RuntimeException) {
            RuntimeException ex = (RuntimeException) e;
            stringBuilder.append(ex.getMessage());
        } else {
           e.printStackTrace();
            stringBuilder.append("未知错误:").append(e.getMessage());
            flag = "error";
        }
        return "error".equals(flag) ? ResultCode.createResultCodeByMeg(stringBuilder.toString()) : ResultCode.createResultCodeByMeg(stringBuilder.toString());
    }

    /**
     * Deal no such message exception string.
     *
     * @param e1 the e 1
     * @return the string
     */
    private String dealNoSuchMessageException(Exception e1) {
        if (e1 instanceof NoSuchMessageException) {
            NoSuchMessageException no = (NoSuchMessageException) e1;
            String message = no.getMessage();
            if (!StringUtils.isEmpty(message)) {
                String[] split = message.split("'");
                if (split.length >= 2) {
                    return split[1];
                }
            }

        }
        return "";
    }
}
