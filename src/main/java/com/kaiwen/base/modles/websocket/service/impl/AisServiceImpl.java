package com.kaiwen.base.modles.websocket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kaiwen.base.modles.websocket.common.Constants;
import com.kaiwen.base.modles.websocket.service.AisService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
public class AisServiceImpl implements AisService {


    @Override
    public JSONObject aisShipParser(String message) {
        String[] split = message.split(",");
        if (split.length == 21 && split[0].equals("$AISORG")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mmsi", split[1]); //MMSI号
            jsonObject.put("imo", split[2]); //IMO编号
            jsonObject.put("shipname", split[3]); //船舶名称
            jsonObject.put("callsign", split[4]); //呼号

            jsonObject.put("devicetype", split[5]); //AIS设备类型编码
            //AIS设备类型 中文说明
            String devicetypeString = Constants.aisStatusMap.get(split[5]);
            jsonObject.put("devicetypeString", StringUtils.isEmpty(devicetypeString) ? "未定义" : devicetypeString);

            jsonObject.put("status", split[6]); //航行状态
            jsonObject.put("statusString", Constants.shipStatusMap.get(split[6]));
            jsonObject.put("speed", split[7]); //对地航速
            jsonObject.put("course", split[8]); //对地航向

            //艏向
            String heading = null;
            if(!StringUtils.isEmpty(split[9])){
                Integer integer = Integer.valueOf(split[9]);
                if(integer>360 || integer<0){
                    heading = "0";
                }else {
                    heading = split[9];
                }
            }
            jsonObject.put("heading", heading);

            jsonObject.put("longitude", split[10]); //经度
            jsonObject.put("latitude", split[11]); //纬度
            jsonObject.put("shiplength", split[12]); //船长
            jsonObject.put("shipwidth", split[13]); //船宽

            //船货类型
            jsonObject.put("cargotype", split[14]);
            String cargotypeString = Constants.shipTypeMap.get(split[14]);
            jsonObject.put("cargotypeString", StringUtils.isEmpty(cargotypeString) ? "未定义" : cargotypeString);

            // 转换时间格式 07-12/15:32 --> 2019-07-12 15:32
            String expectarrivetime = null;
            if (!StringUtils.isEmpty(split[15])) {
                String[] dateTime = split[15].split("/");
                if (dateTime.length == 2) {
                    String date = dateTime[0];
                    String time = dateTime[1];
                    Integer year = LocalDate.now().getYear();
                    expectarrivetime = String.format("%d-%s %s", year, date, time);
                }
            }

            jsonObject.put("expectarrivetime", expectarrivetime); //预计到达时间
            jsonObject.put("maxdraftdepth", split[16]); //最大吃水深度
            jsonObject.put("destination", split[17]); //目的地
            jsonObject.put("shippeople", split[18]); //船载人数

            // 转换转向率 +165.0 --> 右转165.0
            String turnrateString = null;
            if (!StringUtils.isEmpty(split[19])) {
                String sub1 = split[19].substring(0, 1);
                String sub2 = split[19].substring(1);

                turnrateString = ("+".equals(sub1) ? "右转" : "左转") + sub2 + "度";
            }
            jsonObject.put("turnrate", split[19]); //转向率
            jsonObject.put("turnrateString", turnrateString);

            //电子定位设备类型
            String positionType = null;
            if (!StringUtils.isEmpty(split[20])) {
                if (Integer.valueOf(split[20]) >= 9 && Integer.valueOf(split[20]) <= 14) {
                    positionType = "bd";
                }else{
                    positionType = "others";
                }
            }
            jsonObject.put("positionType", positionType);

            return jsonObject;
        }
        return null;
    }


}
