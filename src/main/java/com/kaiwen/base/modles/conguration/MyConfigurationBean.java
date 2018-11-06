package com.kaiwen.base.modles.conguration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author: liangjinyin
 * @Date: 2018-11-06
 * @Description:
 */
@Configuration
@Slf4j
public class MyConfigurationBean {
   @Bean("aa")
    public String info() {
        log.info("my configuration bean");
        return "ooo";
    }
}
