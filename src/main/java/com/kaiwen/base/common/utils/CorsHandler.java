package com.kaiwen.base.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: liangjinyin
 * @Date: 2018-08-27
 * @Description:
 */
public class CorsHandler {

    /**
     * 过滤域名
     * @param response
     * @param request
     * @param curls 配置文件中的curls
     */
    public static Boolean addCorsMapping(HttpServletResponse response, HttpServletRequest request, String curls) {
        Boolean flag = false;
        String []  allowDomain = StringUtils.split(curls,",");
        Set<String> allowedOrigins = new HashSet(Arrays.asList(allowDomain));
        String originHeader = request.getHeader("Origin");

        if (allowedOrigins.contains(originHeader)){
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            flag = true;
        }
        return flag;
    }
}
