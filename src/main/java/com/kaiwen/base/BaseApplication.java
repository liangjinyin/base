package com.kaiwen.base;

import com.kaiwen.base.modles.swagger.EnableMySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: liangjinyin
 * @date:  2018/9/21 14:22
 * @description:
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableMySwagger
public class BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
