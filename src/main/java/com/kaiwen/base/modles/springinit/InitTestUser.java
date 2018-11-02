package com.kaiwen.base.modles.springinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: liangjinyin
 * @Date: 2018-11-02
 * @Description:
 */
@Component
public class InitTestUser {

    @Autowired
    private InitTest initTest;

    public String getInitTest(){
        String key = InitContextHolder.getInit();
        return initTest.get(key);
    }
}
