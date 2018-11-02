package com.kaiwen.base.modles.springinit;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: liangjinyin
 * @Date: 2018-11-02
 * @Description:
 */
@Order(1)
@Component
@Slf4j
@Aspect
public class InitTestAspect {

    @Before("@annotation(com.kaiwen.base.modles.springinit.InitTestAnnotation)")
    public void beforeDSAnnotation(JoinPoint point){
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        InitEnum dsEnum = null;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在注解
            if (method.isAnnotationPresent(InitTestAnnotation.class)) {
                InitTestAnnotation annotation = method.getAnnotation(InitTestAnnotation.class);
                dsEnum = annotation.value();
                InitContextHolder.setInit(dsEnum.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}",getClass().getSimpleName(),e.getMessage());
        }
    }

    @After("@annotation(com.kaiwen.base.modles.springinit.InitTestAnnotation)")
    public void afterDSAnnotation() {
        //还原数据
        InitContextHolder.clearInit();
    }
}
