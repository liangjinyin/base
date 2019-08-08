package com.kaiwen.base.modles.websocket.redis;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liangjinyin
 * @Date: 2019-08-05
 * @Description:
 */
@Service
public class RedisAof {
    private static final Integer ONE = 1;
    public static void main(String[] args) throws Exception{

        Map<String, Integer> map = new HashMap<String, Integer>();
        String s = "Spark \\xe5\\x92\\x8c Hadoop \\xe4\\xbd\\xa0\\xe5\\xa5\\xbd".replaceAll("\\\\x", "%");
        System.out.println(URLDecoder.decode(s, "UTF-8"));
        String sdd = "sdfsd 车险";
        System.out.println(URLEncoder.encode(sdd,"UTF-8"));
        /* 读取数据 */
        try {
           BufferedReader br = new BufferedReader(//D:/javaTools/redis/redis_win/Redis-x64-3.2.100/aof
                        new InputStreamReader(new FileInputStream(new File("D:/javaTools/redis/redis_win/Redis-x64-3.2.100/aof/ss.aof")), "UTF-8"));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E:/value_map.txt")),
                        "UTF-8"));
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {//数据以逗号分隔
                    String encode = lineTxt;
                if (StringUtils.isNotBlank(encode) && StringUtils.startsWith(encode,"{") && StringUtils.endsWith(encode,"}")){
                    JSONObject jsonObject = JSONObject.parseObject(encode);
                    if (StringUtils.isNotBlank(jsonObject.getString("turnrate"))){
                        String turnrate = jsonObject.getString("turnrate");
                        String status = jsonObject.getString("status");
                        String cargotype = jsonObject.getString("cargotype");
                        jsonObject.replace("turnrate",turnrate,URLEncoder.encode(turnrate,"UTF-8"));
                        jsonObject.replace("status",status,URLEncoder.encode(status,"UTF-8"));
                        jsonObject.replace("cargotype",status,URLEncoder.encode(cargotype,"UTF-8"));
                    }
                    if (StringUtils.isNotBlank(jsonObject.getString("diffStationMode"))) {
                        String diffStationMode = jsonObject.getString("diffStationMode");
                        String ggaSolutionType = jsonObject.getString("ggaSolutionType");

                        jsonObject.replace("diffStationMode",diffStationMode,URLEncoder.encode(diffStationMode,"UTF-8"));
                        jsonObject.replace("ggaSolutionType",ggaSolutionType,URLEncoder.encode(ggaSolutionType,"UTF-8"));
                    }
                    encode = jsonObject.toJSONString();
                }
                bw.write(encode);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (Exception e) {
            System.err.println("read errors :" + e);
        }
    }
}