package com.kaiwen.base.modles.ship;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author: liangjinyin
 * @Date: 2019-07-22
 * @Description: 船舶位置点实体类
 */
@Data
//@Entity
public class ShipPoint {
    private Integer id;
    private String longitude;
    private String latitude;
    private String imms;
    private String data;
}
