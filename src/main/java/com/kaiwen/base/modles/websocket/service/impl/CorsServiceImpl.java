package com.kaiwen.base.modles.websocket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kaiwen.base.modles.websocket.common.Constants;
import com.kaiwen.base.modles.websocket.service.CorsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author: liangjinyin
 * @Date: 2019-07-24
 * @Description:
 */
@Service
public class CorsServiceImpl implements CorsService {


    @Override
    public JSONObject corsUserParser(String message) {
        String[] split = message.split(",");
        if (split.length >= 29 && split[0].equals("$CORS")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", split[1]); //id编号
            jsonObject.put("userName", split[2]); //用户名
            jsonObject.put("password", split[3]); //密码
            jsonObject.put("onlineTime", split[4]); //在线时长
            jsonObject.put("loginTimes", split[5]); //登录次数

            //过期时间戳
            String expireTimestamp = null;
            if(!StringUtils.isEmpty(split[6])) {
                Instant instant = Instant.ofEpochSecond(Long.valueOf(split[6]));
                expireTimestamp = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            jsonObject.put("expireTimestamp", expireTimestamp); //过期时间戳

            //差分模式
            String diffModeString = null;
            if (!StringUtils.isEmpty(split[7])){
                if (Integer.valueOf(split[7]) == 1 || Integer.valueOf(split[7]) == 3){
                    diffModeString = "单基站";
                }else if(Integer.valueOf(split[7]) == 2){
                    diffModeString = "虚拟站";
                }else {
                    diffModeString = "未定义";
                }
            }
            jsonObject.put("diffStationMode", split[7]); //差分模式
            jsonObject.put("diffStationModeString", diffModeString);

            jsonObject.put("diffStationIntervalMode", split[8]); //基站编码

            //更新时间戳
            String diffUpdateTm = null;
            if(!StringUtils.isEmpty(split[9])) {
                Instant instant = Instant.ofEpochSecond(Long.valueOf(split[9]));
                diffUpdateTm = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            jsonObject.put("diffUpdateTm", diffUpdateTm); //更新时间戳
            jsonObject.put("diffStationTriangleCode", split[10]); //基站或三角网编码
            jsonObject.put("onlineStatus", split[11]); //在线状态
            jsonObject.put("latestPositionB", split[12]); //最新的位置纬度
            jsonObject.put("latestPositionL", split[13]); //最新的位置经度
            jsonObject.put("latestPositionH", split[14]); //最新的位置高程
            jsonObject.put("location", split[15]); //最新的位置GEO

            //GGA的最新解算状态
            jsonObject.put("ggaSolutionType", split[16]);
            String ggaTypeString = Constants.corsGgaMap.get(split[16]);
            jsonObject.put("ggaSolutionTypeString", StringUtils.isEmpty(ggaTypeString) ? "未定义" : ggaTypeString);

            //最后一次在线时间
            String lastTimeOnline = null;
            if(!StringUtils.isEmpty(split[17])) {
                Instant instant = Instant.ofEpochSecond(Long.valueOf(split[17]));
                lastTimeOnline = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            jsonObject.put("lastTimeOnline", lastTimeOnline); //最后一次在线时间
            jsonObject.put("diffStationIndex", split[18]); //GGA中当前基站索引号
            jsonObject.put("deviceUploadBytes", split[19]); //流动站上发数据字节总数
            jsonObject.put("deviceDownBytes", split[20]); //下发数据到流动站字节数
            jsonObject.put("ggaNumsTotal", split[21]); //上传的GGA总数
            jsonObject.put("ggaRtkFixTotal", split[22]); //固定解GGA总数
            jsonObject.put("ephNumsTotal", split[23]); //下发差分改正数次数
            jsonObject.put("ggaDiffDelay", split[24]); //GGA中差分延时
            jsonObject.put("ephInfo", split[25]); //下发差分卫星数信息
            jsonObject.put("accessPointName", split[26]); //使用的接入点名称

            //用户类型
            String userTypeString = null;
            if (!StringUtils.isEmpty(split[27])){
                userTypeString = Integer.valueOf(split[27]) == 0 ? "rtk" : "未定义";
            }
            jsonObject.put("userType", split[27]); //用户类型
            jsonObject.put("userTypeString", userTypeString);

            //定位类型
            String positionType = null;
            if (!StringUtils.isEmpty(split[28])){
                positionType = Integer.valueOf(split[28]) == 0 ? "bd" : "others";
            }
            jsonObject.put("positionType", positionType); //定位类型

            return jsonObject;
        }
        return null;
    }
}
