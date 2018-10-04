package com.kaiwen.base.modles.feign;

import com.netflix.hystrix.HystrixCommandProperties;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liangjinyin
 * @Date: 2018-10-04
 * @Description:
 */
@Configuration
public class FeignConfig {

    @Value("${application.cnode.url}")
    private String baseUrl;

    @Bean
    FeignClient feignClient() throws InterruptedException {
        return HystrixFeign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .setterFactory((target, method) ->
                        new SetterFactory.Default().create(target, method).
                                andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().
                                        withExecutionTimeoutInMilliseconds(10000)))
                .target(FeignClient.class, this.baseUrl);
    }
}
