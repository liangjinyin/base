package com.kaiwen.base.common.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * @Title: 几何图形操作类
 * @Description:
 * @Author:CHENHUI-PC
 * @Since:2018年11月16日
 * @Version:1.1.0
 */
public class GeometryUtil {
    
    /**
     * 地球半径
     */
    private final static double EARTH_RADIUS = 6378.137;
    
    /**
     * @return GeometryFactory
     * @Description: 初始化几何图形操作工厂（设定坐标系，精度）
     */
    public static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);;
    
    /**
     * @param geometry 几何图形
     * @return wkt字符串
     * @Description: 将图形对象转换成wkt字符串
     */
    public static String convertGeometryToWKT(Geometry geometry) {
        if (geometry != null) {
            WKTWriter wktWriter = new WKTWriter();
            return wktWriter.write(geometry);
        }
        return "";
    }
    
    /**
     * @param d 弧度
     * @return 角度
     * @Description:弧度->角度
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    
    /**
     * @param lat1 点1的纬度
     * @param lng1 点1的经度
     * @param lat2 点2的纬度
     * @param lng2 点2的经度
     * @return 两点的距离
     * @Description: 计算两点的距离（地理坐标）
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math
            .sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        return s;
    }

    /**
     * 左下右上点
     * @param lat1 点1的纬度
     * @param lng1 点1的经度
     * @param lat2 点2的纬度
     * @param lng2 点2的经度
     * @return 中间点
     */
    public static double[] getMiddlePoint(double lng1, double lat1, double lng2, double lat2) {
        if(lat1 <= lat2 || lng1 <= lng2){
            return null;
        }
        double lat = (lat1-lat2)/2 + lat2;
        double lng = (lng1-lng2)/2 + lng2;
        return new double[]{lng, lat};
    }
}
