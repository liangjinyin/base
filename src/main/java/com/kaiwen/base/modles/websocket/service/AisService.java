package com.kaiwen.base.modles.websocket.service;

import com.alibaba.fastjson.JSONObject;

public interface AisService {

    JSONObject aisShipParser(String message);

}
