package com.kaiwen.base.common.scheduled;

import com.kaiwen.base.modles.feign.FeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: liangjinyin
 * @Date: 2018-10-04
 * @Description:
 */
@Slf4j
@Component
public class ScheduledService {

    @Autowired
    private FeignClient feignClient;

    /*@Scheduled(cron = "0 0/15 * * * ?")
    public void scheduled(){
        Object date = feignClient.getDate();
        System.out.println(date);
        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }
    @Scheduled(fixedRate = 1000*60*15)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }*/

    //@Scheduled(cron = "0 0/15 * * * ?")
    public void scheduled(){
        Object date = feignClient.findMls();

        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }
}
