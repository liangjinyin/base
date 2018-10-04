package com.kaiwen.base.common.aspect;

import com.alibaba.fastjson.JSON;
import com.kaiwen.base.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author: liangjinyin
 * @Date: 2018-08-27
 * @Description:
 */
@Slf4j
@Component
@Aspect
public class ParamAspect {
    /**
     * 获取方法执行时间，和方法参数
     *
     * @param point 查重
     * @return object
     * @throws Throwable 查重
     */
    @Around("execution(* com.kaiwen.base.modles..*Controller.*(..))")
    public Object paramHandle(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        /** 获取方法执行时间*/
        long start = System.currentTimeMillis();
        Object object = point.proceed();
        long times = System.currentTimeMillis() - start;
        log.info("logRequest 请求路径:url={}, 请求参数:params={}, 执行方法耗时:times={} {}",
                request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), times, "毫秒");
        log.info("myLogs{\"url\":\"{}\",\"params\":\"{}\",\"time\":\"{}\",\"logTime\":\"{}\"} ** ", request.getRequestURI(),request.getQueryString(),times, DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));

        return object;
    }
}

