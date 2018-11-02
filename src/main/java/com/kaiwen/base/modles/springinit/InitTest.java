package com.kaiwen.base.modles.springinit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liangjinyin
 * @Date: 2018-11-02
 * @Description: 测试spring启动的初始化
 */
@Component(value = "initTest")
@Slf4j
public class InitTest {

    private Map<String, String> initMap = new HashMap<>(8);

    @PostConstruct
    public void init() {
        initMap.put("a", "张三");
        initMap.put("b", "李四");
        initMap.put("c", "王五");
        initMap.put("d", "赵六");
        log.info("初始化。。。。。。。。");
    }

    public String get(String key) {
        return initMap.get(key);
    }
}
