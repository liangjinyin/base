package com.kaiwen.base.modles.ship.entity;

import com.kaiwen.base.common.modle.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author: liangjinyin
 * @Date: 2019-07-22
 * @Description:船舶 Entity
 */
@Data
@Entity
@Table(name = "ais_ship")
public class Ship extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
/**  '自增id' */
    @Column(name = "id")
    private Integer id;
    /**
     * '经度'
     */
    @Column(name = "longitude")
    private String longitude;
    /**
     * '纬度'
     */
    @Column(name = "latitude")
    private String latitude;
    /**
     * '船舶唯一id'
     */

    @Column(name = "imms")
    private String imms;
    /**
     * '时间'
     */
    @Column(name = "date_time")
    private String dateTime;
}