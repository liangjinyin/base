package com.kaiwen.base.modles.springinit;


import org.springframework.context.ApplicationContext;

/**
 * @author: liangjinyin
 * @Date: 2018-11-02
 * @Description:
 */
public class SpringContextUtils {
    private static ApplicationContext applicationContext;

    /** 获取上下文*/
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /** 设置上下文*/
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

   /** 通过名字获取上下文中的bean*/
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /** 通过类型获取上下文中的bean*/
    public static Object getBean(Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }
}
