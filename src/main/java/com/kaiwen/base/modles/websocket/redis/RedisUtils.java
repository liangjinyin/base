package com.kaiwen.base.modles.websocket.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: liangjinyin
 * @Date: 2019-07-22
 * @Description:
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JedisPool jedisPool;

    // ================================Map=================================

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static RedisUtils getRedisUtils() {

        return new RedisUtils();
    }

    //===============redis操作方法===================
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
     * 方法：根据给定的匹配表达式，从redis获取数据  返回的是list集合
     */
    public List<Object> hashFromRedis(String pattern) throws Exception {
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

}
