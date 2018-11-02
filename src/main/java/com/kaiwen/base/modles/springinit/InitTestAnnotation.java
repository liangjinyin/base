package com.kaiwen.base.modles.springinit;

import java.lang.annotation.*;

/**
 * @author: liangjinyin
 * @Date: 2018-11-01
 * @Description:  切换数据库注释
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD})
public @interface InitTestAnnotation {
    InitEnum value() default InitEnum.TEST_A;
}
