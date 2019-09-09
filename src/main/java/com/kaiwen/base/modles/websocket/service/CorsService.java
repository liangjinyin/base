package com.kaiwen.base.modles.websocket.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: liangjinyin
 * @Date: 2019-07-24
 * @Description:
 */
public interface CorsService {

    JSONObject corsUserParser(String message);
}
