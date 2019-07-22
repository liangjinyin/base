package com.kaiwen.base;

import com.kaiwen.base.modles.conguration.EnableMyConfiguration;
import com.kaiwen.base.modles.swagger.EnableMySwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author: liangjinyin
 * @date:  2018/9/21 14:22
 * @description:
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableMySwagger
@EnableMyConfiguration
@EnableWebSocket
public class BaseApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BaseApplication.class, args);
		ConfigurableApplicationContext context = new SpringApplicationBuilder(BaseApplication.class).run(args);
		//String bean = context.getBean("aa", String.class);
		//System.out.print(bean);
	}
}
