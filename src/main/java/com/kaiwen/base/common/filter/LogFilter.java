package com.kaiwen.base.common.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: liangjinyin
 * @Date: 2018-09-26
 * @Description: 自定义log拦截器
 */
public class LogFilter extends Filter<ILoggingEvent> {

    public static final String LOG_STARTS = "myLogs";
    public static final String LOG_NAME = "com.kaiwen.base.common.aspect.ParamAspect";

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (StringUtils.equals(event.getLoggerName(), LOG_NAME) && StringUtils.startsWith(event.getMessage(), LOG_STARTS)) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
