package com.kaiwen.base.common.configuration.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Message source handler.
 *
 * @author maofs
 * @version 1.0
 * @date 2019 -12-19 16:29:07
 */
@Component
public class MessageSourceHandler {
    /**
     * The Request.
     */
    @Autowired
    private HttpServletRequest request;
    
    /**
     * The Message source.
     */
    @Autowired
    private MessageSource messageSource;
    
    /**
     * Get message string.
     *
     * @param messageKey the message key
     * @return the string
     */
    public String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, RequestContextUtils.getLocale(request));
    }
}
