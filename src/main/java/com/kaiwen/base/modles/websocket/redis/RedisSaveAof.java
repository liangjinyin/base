package com.kaiwen.base.modles.websocket.redis;

import com.alibaba.fastjson.JSONObject;
import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.common.utils.RedisOperateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liangjinyin
 * @Date: 2019-08-05
 * @Description:
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisSaveAof {

    @Autowired
    private RedisOperateUtil redisOperateUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "ValueOperationsBean")
    private ValueOperations valueOperations;

    @RequestMapping("/save")
    public Object saveDate() {
        try {
            Map<String, String> map = new HashMap<>();
            BufferedReader br = new BufferedReader(//D:/javaTools/redis/redis_win/Redis-x64-3.2.100/aof
                    new InputStreamReader(new FileInputStream(new File("D:/javaTools/redis/redis_win/Redis-x64-3.2.100/aof/appendonly0806.aof")), "UTF-8"));
            String lineTxt = null;
            String key = null;
            String value = null;
            while ((lineTxt = br.readLine()) != null) {//数据以逗号分隔 CorsLocation

/*                if (StringUtils.isNotBlank(lineTxt) && StringUtils.startsWith(lineTxt, "AIS:") ||StringUtils.startsWith(lineTxt, "CORS:")
                        ||StringUtils.startsWith(lineTxt, "AisLocation") ||StringUtils.startsWith(lineTxt, "CorsLocation")
                        ||StringUtils.startsWith(lineTxt, "CorsStatic") ||StringUtils.startsWith(lineTxt, "AisStatic")
                ) {
                    key = lineTxt;
                }*/
                if (StringUtils.isNotBlank(lineTxt) &&
                        StringUtils.startsWith(lineTxt, "AisLocation") ||StringUtils.startsWith(lineTxt, "CorsLocation")
                ) {
                    key = lineTxt;
                }
                if (StringUtils.isNotBlank(key)) {
                    if (StringUtils.isNotBlank(lineTxt) && StringUtils.startsWith(lineTxt, "[")
                            && StringUtils.endsWith(lineTxt, "]")) {
                       // value = JSONObject.parseObject(lineTxt).toJSONString();
                       value = lineTxt;
                    }
                    if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                        map.put(key, value);
                        //redisOperateUtil.setOneDataToRedis(key,value);
                        key = null;
                        value = null;
                    }
                }

            }
            if (map.size() > 0) {
                //redisOperateUtil.setDataToRedis(map);
                map.forEach((k, v) ->
                        valueOperations.set(k, v)
                );

            }
            return ResultCode.OPERATION_SUCCESSED;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

}
