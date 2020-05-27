package com.kaiwen.base.common.utils;


import io.netty.channel.ChannelHandler.Sharable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import javax.annotation.Resource;
import java.util.*;

/**
 * redis 操作工具类
 */
@Component
@Sharable  //多线程共享
public class RedisOperateUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisOperateUtil.class);

    @Resource
    private JedisPool jedisPool;

    //若取不到JedisPool，用此方式获取bean
    //private static JedisPool jedisPool = (JedisPool) SpringContextUtil.getBean("jedisPool");


    /**
     * 批量 更新/设置 redis中的数据
     */
    public void setDataToRedis(Map<String, String> map) throws Exception{
        Jedis jedis = null;
        Pipeline pipeline = null; //管道
        try {
            if(jedisPool==null){
                return;
            }
            jedis = jedisPool.getResource();
            pipeline = jedis.pipelined();
            if(!CollectionUtils.isEmpty(map)){
                for (Map.Entry<String, String> entry : map.entrySet()){
                    pipeline.set(entry.getKey(), entry.getValue());
                }
            }
            pipeline.sync();

        } finally {
            if (jedis != null) {
                jedis.close();
            }
            if (pipeline != null) {
                pipeline.close();
            }
        }
    }


    /**
     * 单个 更新/设置 redis中的数据
     */
    public void setOneDataToRedis(String key, String value) throws Exception{
        Jedis jedis = null;
        Pipeline pipeline = null; //管道
        try {
            if(jedisPool==null){
                return;
            }
            jedis = jedisPool.getResource();
            pipeline = jedis.pipelined();
            if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)){
                pipeline.set(key, value);
            }
            pipeline.sync();

        } finally {
            if (jedis != null) {
                jedis.close();
            }
            if (pipeline != null) {
                pipeline.close();
            }
        }
    }


    /**
     * redis中的数据  根据模糊key 批量 or 单个 删除
     */
    public void delRedisDataVague(String key) throws Exception{
        if(!StringUtils.isEmpty(key)){
            Jedis jedis = null;
            Pipeline pl = null;//管道
            try {
                if(jedisPool==null){
                    return;
                }
                jedis = jedisPool.getResource();
                pl = jedis.pipelined();
                Response<Set<String>> keysResponse = pl.keys(key);
                pl.sync();
                Set<String> keys = new HashSet<>();
                if (keysResponse != null) {
                    keys = keysResponse.get();
                }

                if (keys.size() > 0) {
                    for (String k : keys) {
                        pl.del(k);
                    }
                    pl.sync();
                }

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
                if (pl != null) {
                    pl.close();
                }
            }
        }
    }


    /**
     * redis中的数据  根据精确keys 批量删除
     */
    public void delRedisData(Set<String> keys) throws Exception{
        if(!CollectionUtils.isEmpty(keys)){
            Jedis jedis = null;
            Pipeline pl = null;//管道
            try {
                if(jedisPool==null){
                    return;
                }
                jedis = jedisPool.getResource();
                pl = jedis.pipelined();
                for (String key : keys) {
                    // 删除
                    pl.del(key);
                }
                pl.sync();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
                if (pl != null) {
                    pl.close();
                }
            }
        }
    }


    /**
     * 方法：根据给定的key查询一条记录  返回一个String
     */
    public String getOneDataFromRedis(String key) throws Exception {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = null;
        Pipeline pl = null; //管道
        try {
            List<Object> content = new ArrayList<>();
            if(jedisPool==null){
                return null;
            }
            jedis = jedisPool.getResource();
            pl = jedis.pipelined();
            Response<Set<String>> keysResponse = pl.keys(key);
            pl.sync();
            Set<String> keys = new HashSet<>();
            if (keysResponse != null) {
                keys = keysResponse.get();
            }
            if (keys.size() > 0) {
                for (String k : keys) {
                    pl.get(k);
                }
                content = pl.syncAndReturnAll();
            }

            if (!CollectionUtils.isEmpty(content)) {
                return content.get(0).toString(); //返回第一条记录
            }

        }finally {
            if (jedis != null) {
                jedis.close();
            }
            if (pl != null) {
                pl.close();
            }
        }
        return null;
    }


    /**
     * 方法：根据给定的匹配表达式，从redis获取数据  返回的是list集合
     */
    public List<Object> getDataFromRedis(String pattern) throws Exception {
        if (StringUtils.isEmpty(pattern)) {
            return null;
        }
        Jedis jedis = null;
        Pipeline pl = null; //管道
        try {
            if(jedisPool==null){
                return null;
            }
            List<Object> content = new ArrayList<>();
            jedis = jedisPool.getResource();
            pl = jedis.pipelined();
            Response<Set<String>> keysResponse = pl.keys(pattern);
            pl.sync();
            Set<String> keys = new HashSet<>();
            if (keysResponse != null) {
                keys = keysResponse.get();
            }

            if (keys.size() > 0) {
                for (String key : keys) {
                    pl.get(key);
                }
                content = pl.syncAndReturnAll();
            }

            if (!CollectionUtils.isEmpty(content)) {
                return content;
            }

        }finally {
            if (jedis != null) {
                jedis.close();
            }
            if (pl != null) {
                pl.close();
            }
        }
        return null;
    }


    /**
     * redis中的数据  根据精确keys 批量删除
     */
    public List<Object> getRedisData(Set<String> keys) throws Exception{
        if(!CollectionUtils.isEmpty(keys)){
            Jedis jedis = null;
            Pipeline pl = null;//管道
            try {
                if(jedisPool==null){
                    return null;
                }
                List<Object> content = new ArrayList<>();
                jedis = jedisPool.getResource();
                pl = jedis.pipelined();

                if (keys.size() > 0) {
                    Set<String> temp = new HashSet<>();
                    for (String key : keys) {
                        Response<Set<String>> keysResponse = pl.keys(key);
                        pl.sync();
                        if (keysResponse != null) {
                            Set<String> strings = keysResponse.get();
                            if(strings.iterator().hasNext()){
                                String next = strings.iterator().next();
                                temp.add(next);
                            }
                        }
                    }
                    //temp1 = pl.syncAndReturnAll();
                    if (temp.size() > 0) {
                        for (String key : temp) {
                            pl.get(key);
                        }
                        content = pl.syncAndReturnAll();
                    }
                }
                if (!CollectionUtils.isEmpty(content)) {
                    return content;
                }

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
                if (pl != null) {
                    pl.close();
                }
            }
        }
        return null;
    }
}
