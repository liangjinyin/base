package com.kaiwen.base.modles.websocket.entity;

import lombok.Data;

/**
 * @author: liangjinyin
 * @Date: 2019-07-22
 * @Description: 船舶位置信息实体类
 */
@Data
public class AisShip {
    private String maxdraftdepth;
    private String mmsi;
    private String devicetypeString;
    private String heading;
    private String elecequipment;
    private String latitude;
    private String destination;
    private String shipwidth;
    private String shippeople;
    private String imo;
    private String expectarrivetime;
    private String shiplength;
    private String devicetype;
    private String speed;
    private String turnrate;
    private String cargotype;
    private String callsign;
    private String course;
    private String locatetype;
    private String shipname;
    private String status;
    private String longitude;
}