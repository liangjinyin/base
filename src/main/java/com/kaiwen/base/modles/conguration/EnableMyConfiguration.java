package com.kaiwen.base.modles.conguration;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: liangjinyin
 * @Date: 2018-11-06
 * @Description:
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
@Import(MyConfigurationSelector.class)
public @interface EnableMyConfiguration {
}
