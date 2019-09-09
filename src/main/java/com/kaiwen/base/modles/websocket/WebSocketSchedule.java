package com.kaiwen.base.modles.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaiwen.base.common.utils.HttpUtils;
import com.kaiwen.base.common.utils.ParamUtils;
import com.kaiwen.base.modles.ship.entity.Ship;
import com.kaiwen.base.modles.ship.repository.ShipRepository;
import com.kaiwen.base.modles.websocket.client.TcpClient;
import com.kaiwen.base.modles.websocket.entity.AisShipPoint;
import com.kaiwen.base.modles.websocket.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @Class bigscreen_schedule
 * @Date 2019/6/21
 */
//@Component
@Slf4j
public class WebSocketSchedule {

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20); //这里设置的线程数
        return taskScheduler;
    }

    @Value("${cors.http}")
    private String corshttp;

    @Value("${ais.http}")
    private String aishttp;

    private Integer mun = 0;

    @Resource
    private BigScreen_WebSocketHandler webSocketHandler;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ShipRepository shipRepository;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 终端管理平台首页，船舶位置定时推送到前端
     */
    //@Scheduled(cron = "0/10 * *  * * ? ")
    public void pushAisShip() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("aisShip", AdapterCommon.getShipMap().values());
        webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map)));

        //最新的数据缓存到redis 并保存历史数据
        Map<String, Object> entries = redisTemplate.opsForHash().entries("aisShip");
        Map<String, Object> historyMap = new HashMap<>(128);
        JSONObject locationMap = new JSONObject();
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (!CollectionUtils.isEmpty(entries) && entries.size() != 0) {
            // entries.forEach((key, value) -> historyMap.put(key + "_" + format, value));
            entries.forEach((key, value) -> {
                JSONObject ship = JSONObject.parseObject(value.toString());
                locationMap.put("imms", key);
                locationMap.put("longitude", ship.getString("longitude"));
                locationMap.put("latitude", ship.getString("latitude"));
                locationMap.put("dateTime", ship.getString("dateTime"));
                historyMap.put(key + "_" + format, locationMap);
            });
            redisTemplate.opsForHash().putAll("aisShipHistory", historyMap);
        }
        redisTemplate.opsForHash().putAll("aisShip", AdapterCommon.getShipMap());
        log.info(entries.toString());


    }

    /**
     * 从redis取船舶信息保存到数据库中
     */
    //@Scheduled(cron = "0/15 * *  * * ? ")
    public void saveAisShip() throws Exception {
        Map aisShipHistory = redisTemplate.opsForHash().entries("aisShipHistory");
        Collection values = aisShipHistory.values();
        if (!CollectionUtils.isEmpty(values) && values.size() != 0) {
            List<Ship> shipList = new ArrayList<>();
            values.stream().forEach(e -> {
                Ship ship = null;
                try {
                    ship = ParamUtils.json2obj(e.toString(), Ship.class);
                    shipList.add(ship);
                    shipRepository.saveAll(shipList);
                    redisTemplate.expire("aisShipHistory", 1, TimeUnit.SECONDS);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    @Scheduled(cron = "0/2 * *  * * ? ")
    public void saveDataFromRedisToPgsql() throws Exception {
        List<Object> dataFromRedis = redisUtils.getDataFromRedis("AIS:*");
        if (!CollectionUtils.isEmpty(dataFromRedis) && dataFromRedis.size() > 0) {
            List<AisShipPoint> collect = new ArrayList<>();
            Date date = new Date();
            for (Object dataFromRedi : dataFromRedis) {
                if (dataFromRedi == null) {
                    continue;
                }
                JSONObject jsonObject = JSONObject.parseObject(dataFromRedi.toString());

                String status = jsonObject.getString("status");
                if (StringUtils.isEmpty(status) || !"0".equals(status)) { //过滤不在航的船舶 在航--0
                    continue;
                }

                AisShipPoint aisShipPoint = new AisShipPoint();
                aisShipPoint.setMmsi(jsonObject.getString("mmsi"));
                aisShipPoint.setShipName(jsonObject.getString("shipname"));
                aisShipPoint.setLatitude(jsonObject.getDouble("latitude"));
                aisShipPoint.setLongitude(jsonObject.getDouble("longitude"));
                aisShipPoint.setStatus(status);
                aisShipPoint.setSpeed(jsonObject.getDouble("speed"));
                aisShipPoint.setCourse(jsonObject.getDouble("course"));
                aisShipPoint.setHeading(jsonObject.getDouble("heading"));
                aisShipPoint.setPositionType(jsonObject.getString("positionType"));
                aisShipPoint.setReceiveTime(jsonObject.getTimestamp("receiveTime"));
                aisShipPoint.setLaupTime(date);
                aisShipPoint.create();
                collect.add(aisShipPoint);
            }
            //aisShipPointDao.saveAll(collect);
        }
    }

    Double lon = 113.1608;
    Double lat = 29.4767;

    @Scheduled(cron = "0/19 * *  * * ? ")
    public void pushAisShip1() throws Exception {

        TcpClient bootstrap = new TcpClient(8987, "localhost");
        // 130.585444,24.065612 23.1815052,113.416779
        lon += 0.01;
        lat = (mun % 2 == 0 ? lat + 0.03 : lat - 0.01);
        String location = String.format("%s,%s", String.valueOf(lon), String.valueOf(lat));
        //String location1 = String.format("%s,%s", String.valueOf(lat), String.valueOf(lon));
        String loginMsg = "$AISORG,413833356,56546654,CHANGJIANGYIHAO,CHSDDD,ClASS-A,0,7.5,231.3,231," + location + ",25.5,8.4,0,07-12/15:32,5.6,WUHANG,12,+165.0,10\r\n";
        //String loginMsg1= "$CORS,849cdd31314646c9829be1b9df46250k,test,123,20,0,1582905599,2,S031-S032-S033vrs_23.181416_113.416692_15.274148,1562058957,S031-S032-S033,1,"+location+",51.6100006,010100000037AAD381AC5A5C407C0F971C772E3740,4,1562058957,,12021,234,3,3,3,0,,,0,0\r\n";
        bootstrap.socketChannel.writeAndFlush(loginMsg);
        mun++;

    }
}
