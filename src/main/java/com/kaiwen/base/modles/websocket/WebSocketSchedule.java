package com.kaiwen.base.modles.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaiwen.base.common.utils.HttpUtils;
import com.kaiwen.base.common.utils.ParamUtils;
import com.kaiwen.base.modles.ship.entity.Ship;
import com.kaiwen.base.modles.ship.repository.ShipRepository;
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

    @Resource
    private BigScreen_WebSocketHandler webSocketHandler;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private ShipRepository shipRepository;

    /**
     * 基站管理首页，统计数据定时推送到前端
     */
 /*   @Scheduled(cron = "2 0/2 *  * * ? ") //每2分钟的第2秒
    public void pushCORSInfo() throws Exception {
        //异常统计百分比
        String totalAbnormalPercent = null; //总异常占比
        String corsAbnormalPercent = null; //北斗卫星基准站 异常占比
        String corsczAbnormalPercent = null; //北斗卫星监测站 异常占比
        String aisAbnormalPercent = null; //AIS基站 异常占比

        Integer stationCount = 0; //站点总数
        Integer abnormalCount = 0; //异常总数

        Map<String, String> parameters = new HashMap<>();
        String result = HttpUtils.sendPost(corshttp + "/cors_station/data_static", parameters);
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("info")
                    && jsonObject.containsKey("status") && jsonObject.getInteger("status") == 0) {
                jsonObject.remove("status");
                jsonObject.remove("info");
                //统计、异常数据
                Map<String, Object> map = new HashMap<>();
                map.put("corsStaticData", jsonObject);
                webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map)));

                //北斗卫星基准站 异常占比
                if (jsonObject.containsKey("baseCount") && jsonObject.containsKey("baseOnlineCount")) {
                    Integer baseCount = jsonObject.getInteger("baseCount");
                    Integer baseOnlineCount = jsonObject.getInteger("baseOnlineCount");
                    if (baseCount != 0) {
                        abnormalCount += baseCount - baseOnlineCount;
                        stationCount += baseCount;

                        double cors = (double) (baseCount - baseOnlineCount) / baseCount;
                        corsAbnormalPercent = Down4Up5ToPercent(cors);
                    }
                }

                //北斗卫星监测站 异常占比
                if (jsonObject.containsKey("czCount") && jsonObject.containsKey("czOnlineCount")) {
                    Integer czCount = jsonObject.getInteger("czCount");
                    Integer czOnlineCount = jsonObject.getInteger("czOnlineCount");
                    if (czCount != 0) {
                        abnormalCount += czCount - czOnlineCount;
                        stationCount += czCount;

                        double cz = (double) (czCount - czOnlineCount) / czCount;
                        corsczAbnormalPercent = Down4Up5ToPercent(cz);
                    }
                }
            }
        }


        // AIS 统计类信息
        Map<String, String> parameters1 = new HashMap<>();
        String aisResult = HttpUtils.sendGet(aishttp + "/api/AISAbnormal/GetAISAbnormalList", parameters1);
        if (!StringUtils.isEmpty(aisResult)) {
            JSONObject jsonObject = JSONObject.parseObject(aisResult);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("Data")
                    && jsonObject.getInteger("Code") == 1) {
                JSONObject data = jsonObject.getJSONObject("Data");

                //AIS 统计、异常数据
                Map<String, Object> map = new HashMap<>();
                map.put("aisStaticData", data);
                webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map)));

                //AIS基准站 异常占比
                if (data != null && !data.isEmpty() && data.containsKey("BsCount") && data.containsKey("BsOnlieCount")) {
                    Integer bsCount = data.getInteger("BsCount");
                    Integer bsOnlineCount = data.getInteger("BsOnlieCount");
                    if (bsCount != 0) {
                        stationCount += bsCount;
                        abnormalCount += bsCount - bsOnlineCount;

                        double ais = (double) (bsCount - bsOnlineCount) / bsCount;
                        aisAbnormalPercent = Down4Up5ToPercent(ais);
                    }
                }
            }
        }

        //总异常占比
        if (stationCount != 0) {
            totalAbnormalPercent = Down4Up5ToPercent((double) abnormalCount / stationCount);
        }

        //返回异常统计百分比
        Map<String, String> map = new HashMap<>();
        map.put("totalPercent", totalAbnormalPercent);
        map.put("corsPercent", corsAbnormalPercent);
        map.put("corsczPercent", corsczAbnormalPercent);
        map.put("aisPercent", aisAbnormalPercent);
        map.put("aisczPercent", Down4Up5ToPercent((double) 0 / 56));

        Map<String, Object> response = new HashMap<>();
        response.put("abnormalStatic", map);
        webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(response)));
    }*/


    /**
     * 终端管理平台首页，推送AIS船舶统计类信息
     */
    @Scheduled(cron = "33 0/2 *  * * ? ") //每2分钟的第33秒
    public void pushAisCount() throws Exception {
        Map<String, Object> map = new HashMap<>();
        // AIS 北斗定位数和其他定位数
        Map<String, JSONObject> shipMap = AdapterCommon.getShipMap();
        Integer bdCount = AdapterCommon.getHashSet().size();
        Integer otherCount = shipMap.values().size() - AdapterCommon.getHashSet().size();
        map.put("bdCount", bdCount);
        map.put("otherCount", otherCount);

        // 高精度用户总定位数 公网播发总数
        Map<String, String> parameters = new HashMap<>();
        String result = HttpUtils.sendPost(corshttp + "/cors_station/user_list_page", parameters);
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("info") && jsonObject.containsKey("rows")
                    && jsonObject.containsKey("status") && jsonObject.getInteger("status") == 0) {
                jsonObject.remove("status");
                jsonObject.remove("info");
                JSONArray rows = jsonObject.getJSONArray("rows");
                List<Object> data = new ArrayList<>(128);
                // 过滤没有经纬度的高精度用户数据
                rows.forEach(e->{
                    JSONObject data1 = JSONObject.parseObject(e.toString());
                    if (data1.containsKey("latestPositionB") && data1.containsKey("latestPositionL")
                            && data1.containsKey("latestPositionH")) {
                        data.add(data1);
                    }
                });
            }
        }
        Integer corsUserCount = 0;
        map.put("corsUserCount", corsUserCount);

        //终端总数
        map.put("allCount", corsUserCount + bdCount + otherCount);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("aisCountData", map);
        webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map1)));
    }


    /**
     * 基站管理首页，CORS基站位置定时推送到前端
     */
/*    @Scheduled(cron = "44 0/4 *  * * ? ") //每4分钟的第44s执行一次
    public void pushCORSLocation() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("filter_station_type", "");
        parameters.put("filter_station_status", "");
        String result = HttpUtils.sendPost(corshttp + "/cors_station/data_map", parameters);
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null && !jsonObject.isEmpty()
                    && jsonObject.containsKey("status") && jsonObject.getInteger("status") == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("map");
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("corsLocation", jsonArray);
                    webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map)));
                }
            }
        }
    }*/


    /**
     * 基站管理首页，AIS基站位置定时推送到前端
     */
/*    @Scheduled(cron = "5 0/5 *  * * ? ") //每5分钟的第5s执行一次
    public void pushAISLocation() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        String result = HttpUtils.sendGet(aishttp + "/api/AISInfo/GetAISInfoList", parameters);
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("Data")
                    && jsonObject.getInteger("Code") == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("aisLocation", jsonArray);
                    webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map)));
                }
            }
        }
    }*/


    /**
     * 终端管理平台首页，高精度用户位置定时推送到前端
     */
/*    @Scheduled(cron = "0/10 * *  * * ? ")
    public void pushCORSUser() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("PAGE_NUM", "");
        parameters.put("num_per_page", "");
        String result = HttpUtils.sendPost(corshttp + "/cors_station/user_list_page", parameters);
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("info") && jsonObject.containsKey("rows")
                    && jsonObject.containsKey("status") && jsonObject.getInteger("status") == 0) {
                jsonObject.remove("status");
                jsonObject.remove("info");

                // 过滤没有经纬度的高精度用户数据
                JSONArray rows = jsonObject.getJSONArray("rows");
                List<Object> data = new ArrayList<>(128);
                List<List<Object>> collect = rows.stream().map(e -> {
                    JSONObject data1 = JSONObject.parseObject(e.toString());
                    if (data1.containsKey("latestPositionB") && data1.containsKey("latestPositionL")
                            && data1.containsKey("latestPositionH")) {
                        data.add(data1);
                    }
                    return data;
                }).collect(Collectors.toList());
                Map<String, Object> map = new HashMap<>();
                map.put("corsUser", CollectionUtils.isEmpty(collect) ? null : collect.get(0));
                webSocketHandler.sendMsgToAll(new TextMessage(ParamUtils.obj2Str(map)));
            }
        }
    }*/


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

    //四舍五入 转成百分比（保留两位小数）字符串
    private String Down4Up5ToPercent(Double decimal) {
        if (decimal != null) {
            DecimalFormat df = new DecimalFormat("0.00%");
            BigDecimal b = new BigDecimal(decimal);
            double value = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            return df.format(value);
        }
        return null;
    }


}
