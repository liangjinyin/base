package com.kaiwen.base.modles.websocket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaiwen.base.modles.websocket.utils.GeometryUtil;
import com.kaiwen.base.modles.websocket.utils.PointUtil;
import com.vividsolutions.jts.geom.Point;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * AIS船舶 点表
 */
@Entity
@Data
public class AisShipPoint implements Serializable {
    private static final long serialVersionUID = 4690413190679323073L;

    @Id
    @GenericGenerator(name = "gid", strategy = "uuid")
    @GeneratedValue(generator = "gid")
    @Column(name = "id", length = 36, nullable = false, unique = true)
    private String id; //主键

    @Column(name = "mmsi")
    private String mmsi;  // MMSI号

    @Column(name = "shipname")
    private String shipName;// 船舶名称

    @Column(name = "speed")
    private Double speed;   // 对地航速

    @Column(name = "course")
    private Double course;   // 对地航向

    @Column(name = "heading")
    private Double heading;   // 艏向

    @Column(name = "longitude")
    private Double longitude;// 经度

    @Column(name = "latitude")
    private Double latitude;// 纬度

    @Column(name = "positionType")
    private String positionType; // 定位类型

    @Column(name = "status")
    private String status;// 状态

    //服务器接收时间
    @Column(name = "receivetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;

    //最后操作时间
    @Column(name = "lauptime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date laupTime;


    //图形数据 CGCS2000
    @Column(name = "geom_cgcs2000", columnDefinition = "geometry")
    @JsonIgnore
    private Point geomCgcs2000;


    //用于轨迹查询：开始时间
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonIgnore
    private Date startTime;

    //用于轨迹查询：结束时间
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonIgnore
    private Date endTime;

    //几何图形数据：wkt格式
    public String getGeomwkt() {
        if (getGeomCgcs2000()==null || getGeomCgcs2000().isEmpty()) {
            return null;
        }
        return GeometryUtil.convertGeometryToWKT(getGeomCgcs2000());
    }

    //创建geometry类型 点数据
    public void create(){
        if(getLongitude() != null && getLatitude() != null){
            Point point = PointUtil.creatPointByCors(getLongitude(), getLatitude());
            if (point != null){
                setGeomCgcs2000(point);
            }
        }
    }



}
