package com.kaiwen.base.modles.websocket.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @Title: Point操作类
 * @Description:
 * @Author:CHENHUI-PC
 * @Since:2018年11月16日
 * @Version:1.1.0
 */
public class PointUtil {
    /**
     * @return GeometryFactory
     * @Description: 初始化几何图形操作工厂（设定坐标系，精度）
     */
    private static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);;
    
    /**
     * @param wkt
     * @return Point
     * @throws ParseException
     * @Description: wkt转point
     */
    public static Point creatPointBywkt(String wkt)
        throws ParseException {
        WKTReader wktReader = new WKTReader(geometryFactory);
        Point point = (Point)wktReader.read(wkt);
        return point;
    }
    
    /**
     * @param x x坐标  经度
     * @param y y坐标  纬度
     * @return Point
     * @Description: 通过经纬度构建点
     */
    public static Point creatPointByCors(Double x, Double y) {
        Coordinate coordinate = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(coordinate);
        return point;
    }
    

}
