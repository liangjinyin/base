package com.kaiwen.base.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ParamUtils {
    
    // 支持json
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /**
     * 对象转换成Json字符串
     */
    public static String obj2Str(Object obj)
        throws Exception {
        return objectMapper.writeValueAsString(obj);
    }
    
    /**
     * Json字符串转换成Map<String, ?>
     */
    public static Map<String, Object> json2map(String jsonStr)
        throws Exception {
        return objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }
    
    /**
     * Json字符串转换成T
     */
    public static <T> T json2obj(String jsonStr, Class<T> clazz)
        throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }
    
    /**
     * Map转换成T
     */
    public static <T> T map2obj(Map<String, Object> map, Class<T> clazz)
        throws Exception {
        String jsonStr = obj2Str(map);
        return objectMapper.readValue(jsonStr, clazz);
    }
    
    /**
     * 对象转换成Map
     */
    public static Map<String, Object> obj2Map(Object obj)
        throws Exception {
        String jsonStr = obj2Str(obj);
        return json2map(jsonStr);
    }
    
    /**
     * 获取请求参数，需要完善，支持get方法，post方法（headers 的 Content-Type 为
     */
    public static Map<String, Object> getParamMap(HttpServletRequest request)
        throws Exception {
        String methodName = request.getMethod();
        if ("POST".equalsIgnoreCase(methodName)) {
            String contentType = request.getHeader("Content-Type");
            if (contentType != null && contentType.indexOf("application/json") >= 0) {//post方式参json
                String jsonStr = parseJsonParams(request);
                return json2map(jsonStr);
            }
        }
        Map<String, String[]> paramMap = request.getParameterMap();
        return convertParamMap(paramMap);
    }
    
    /**
     * 转换参数格式
     */
    public static Map<String, Object> convertParamMap(Map<String, String[]> paramMap) {
        Map<String, Object> result = new HashMap<>();
        Set<Entry<String, String[]>> entryset = paramMap.entrySet();
        if (entryset.size() > 0) {
            for (Entry<String, String[]> entry : entryset) {
                String key = entry.getKey();
                String[] valueArr = entry.getValue();
                String value = valueArr.length > 0 ? valueArr[0] : null;
                result.put(key, value);
            }
        }
        return result;
    }
    
    /**
     * 解析request中的json格式的参数
     */
    public static String parseJsonParams(HttpServletRequest request)
        throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        return responseStrBuilder.toString();
    }
    
    /**
     * 判定map是否为空
     */
    public static boolean mapIsEmpty(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 获取int数据
     */
    public static Integer getIntParam(String key, Map<String, Object> paramMap) {
        if (!mapIsEmpty(paramMap)) {
            Object value = paramMap.get(key);
            if (value == null) {
                return null;
            }
            return Integer.parseInt(value.toString());
        } else {
            return null;
        }
    }
    
    /**
     * 获取double型数据
     */
    public static Double getDoubleParam(String key, Map<String, Object> paramMap) {
        if (!mapIsEmpty(paramMap)) {
            Object value = paramMap.get(key);
            if (value == null) {
                return null;
            }
            return Double.parseDouble(value.toString());
        } else {
            return null;
        }
    }
    
    /**
     * 获取浮点型数据
     */
    public static Float getFloatParam(String key, Map<String, Object> paramMap) {
        if (!mapIsEmpty(paramMap)) {
            Object value = paramMap.get(key);
            if (value == null) {
                return null;
            }
            return Float.parseFloat(value.toString());
        } else {
            return null;
        }
    }
    
    /**
     * 获取字符串
     */
    public static String getStringParam(String key, Map<String, Object> paramMap)
        throws Exception {
        if (!mapIsEmpty(paramMap)) {
            Object value = paramMap.get(key);
            if (value == null) {
                return null;
            }
            return value.toString();
        } else {
            return null;
        }
    }
}
