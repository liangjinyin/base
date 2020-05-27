package com.kaiwen.base.modles.websocket.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.common.modle.controller.BaseController;
import com.kaiwen.base.common.utils.GeometryUtil;
import com.kaiwen.base.common.utils.RedisOperateUtil;
import com.kaiwen.base.modles.ship.entity.Ship;
import com.kaiwen.base.modles.websocket.utils.PointUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @author: liangjinyin
 * @Date: 2019-08-05
 * @Description:
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisSaveAof extends BaseController {

    @Autowired
    private RedisOperateUtil redisOperateUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "ValueOperationsBean")
    private ValueOperations<String, String> valueOperations;



    // 备份redis 数据，注意数据库的选择！！！！
    @RequestMapping("/save")
    public Object saveDate() {
        try {

            Map<String, String> map = new HashMap<>();

            for (int i = 0; i < 20; i++) {
                BufferedReader br = new BufferedReader(//D:/javaTools/redis/redis_win/Redis-x64-3.2.100/aof
                        new InputStreamReader(new FileInputStream(new File("D:/javaTools/redis/redis_win/Redis-x64-3.2.100/aof/hh.aof")), "UTF-8"));
                String lineTxt = null;
                String key = null;
                String value = null;
                while ((lineTxt = br.readLine()) != null) {//数据以逗号分隔 CorsLocation

                    if (StringUtils.isNotBlank(lineTxt) &&
                            StringUtils.startsWith(lineTxt, "AIS")//AIS:100900013_others
                    ) {
                        key = lineTxt ;
                    }
                    if (StringUtils.isNotBlank(key)) {
                        if (StringUtils.isNotBlank(lineTxt) && StringUtils.startsWith(lineTxt, "{") && StringUtils.endsWith(lineTxt, "}")) {
                            //  复制添加数据到redis 进行测试
                            JSONObject jsonObject = JSONObject.parseObject(lineTxt);
                            key = "AIS:" + jsonObject.getString("mmsi") +"00"+ (i+1) + "_others";
                            Double lat = jsonObject.getDouble("latitude") + 0.01*(i+1);
                            Double lon = jsonObject.getDouble("longitude")+ 0.01*(i+1);
                            String mmsi = jsonObject.getString("mmsi") +"00"+ (i+1);
                            String shipname = jsonObject.getString("shipname") +"00"+ (i+1);
                            jsonObject.put("latitude", lat);
                            jsonObject.put("longitude", lon);
                            jsonObject.put("mmsi", mmsi);
                            jsonObject.put("shipname", shipname);

                            value = jsonObject.toJSONString();

                        }
                        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                            map.put(key, value);
                            //redisOperateUtil.setOneDataToRedis(key,value);
                            key = null;
                            value = null;
                        }
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

    // 测试redis GEOHASH算法
    @GetMapping("/hash")
    public String testHash()throws Exception{


        // 114.23268127441406 30.672317505930554,114.13490297924727 30.535675044637177,114.36067201383412 30.477310176473114,114.57875061035156 30.669570923899304,114.23268127441406 30.672317505930554
        double[] middlePoint = GeometryUtil.getMiddlePoint(114.36067201383412d, 30.672317505930554d, 114.23268127441406, 30.477310176473114);

        double distance = GeometryUtil.getDistance(114.36067201383412d, 30.672317505930554d, 114.23268127441406, 30.477310176473114);

        if(middlePoint != null){
            GeoResults<RedisGeoCommands.GeoLocation<Object>> rangeDistance = getRangeDistance(middlePoint[0], middlePoint[1], distance, 100);
            List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = rangeDistance.getContent();
            Set<String> set = new HashSet<>();
            content.forEach(e->{
                RedisGeoCommands.GeoLocation<Object> content1 = e.getContent();
                String name = (String)content1.getName();
                //Object o = redisTemplate.opsForValue().get(name);
                //redisOperateUtil.getOneDataFromRedis()
                set.add("AIS:"+name+"*");
                //String oneDataFromRedis = redisOperateUtil.getOneDataFromRedis("AIS:" + name + "*");

            });
            //set.forEach(e);
            /*List<Object> redisData = redisOperateUtil.getRedisData(set);
            List<Ship> ships = JSONArray.parseArray(redisData.toString(), Ship.class);
            data = ships;
            System.out.println(redisData.toString());*/
        }



       /* List<Object> objectList = redisOperateUtil.getDataFromRedis("AIS:*");
        Map<String, Integer> map = new HashMap<>();
        objectList.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            Double longitude = jsonObject.getDouble("longitude");
            Double latitude = jsonObject.getDouble("latitude");
            if(longitude <135 && latitude < 53 && longitude > 73 && latitude > 4){
                Point point = new Point(longitude, latitude);
                String value = jsonObject.getString("mmsi");
                //redisTemplate.opsForSet().add("shipsetkey", value);

                //redisTemplate.opsForGeo().add("shipGeo", point, value);
            }
        });*/

       /* Set<Object> shipsetkey = redisTemplate.opsForSet().members("shipsetkey");
        Set<String> set = new HashSet<>();
        shipsetkey.forEach(e->{
            GeoResults<RedisGeoCommands.GeoLocation<Object>> rangeDistanceByMember = getRangeDistanceByMember(e.toString(), distance, 100);
            List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = rangeDistanceByMember.getContent();

        });*/


        /*Distance distance = new Distance(5, Metrics.KILOMETERS);// 5 公里
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo().radius("shipGeo","413788092",distance,args);
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = results.getContent();*/

        //System.out.println(content);

        return result();
    }

    // 根据给定成员获取指定范围内的地理位置集合
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getRangeDistanceByMember(String member, Double range, int count) {

        BoundGeoOperations<String, Object> boundGeoOps = redisTemplate.boundGeoOps("shipGeo");
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = boundGeoOps.radius(member, new Distance(range, Metrics.KILOMETERS), RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().sortDescending().limit(count));
        return geoResults;

    }
    // 根据给定地理位置坐标获取指定范围内的地理位置集合
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getRangeDistance(Double lng, Double lat, Double range, int count) {
        BoundGeoOperations<String, Object> boundGeoOps = redisTemplate.boundGeoOps("shipGeo");
        Circle circle = new Circle(new Point(lng, lat), new Distance(range, Metrics.KILOMETERS));
        //可以指定分页数量,以及排序规则
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius = boundGeoOps.radius(circle,
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().sortDescending().limit(count));
        return geoRadius;
    }
}
