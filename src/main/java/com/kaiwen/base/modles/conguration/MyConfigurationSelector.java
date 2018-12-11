package com.kaiwen.base.modles.conguration;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: liangjinyin
 * @Date: 2018-11-06
 * @Description:
 */
public class MyConfigurationSelector implements ImportSelector {
    /**
     *
     * @param annotationMetadata 是启动类上面的注解
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{MyConfigurationBean.class.getName()};
    }
}
