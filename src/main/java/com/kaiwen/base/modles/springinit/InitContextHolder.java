package com.kaiwen.base.modles.springinit;

import org.springframework.stereotype.Component;

/**
 * @author: liangjinyin
 * @Date: 2018-11-02
 * @Description:
 */
@Component
public class InitContextHolder {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getInit() {
        return threadLocal.get();
    }

    public static void setInit(String value) {
        threadLocal.set(value);
    }

    public static void clearInit() {
        threadLocal.remove();
    }

}
