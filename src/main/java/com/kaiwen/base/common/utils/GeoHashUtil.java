package com.kaiwen.base.common.utils;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Point;

/**
 * DOTO
 *
 * @author icxx
 * @version 1.0
 * @date 2019 -02-18 11:36:26
 */
public class GeoHashUtil {
    public static String encode(double latitude, double longitude, int precision){
        return GeohashUtils.encodeLatLon(latitude,longitude,precision);
    }

    public static Double[] decode(String geohast){
        SpatialContext ctx = SpatialContext.GEO;
        Point point = GeohashUtils.decode(geohast, ctx);
        return new Double[]{point.getX(),point.getY()};
    }
    /*public static Double getDistance(){
        GeohashUtils.
    }*/
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // wx4g8c9v wx4g3pc4 wx4g3p wx4g3r
        System.out.println(encode(39.989375, 116.422843,6));
        Double[] doublearr = decode("ws0b");
        System.out.println("经度："+doublearr[0]+" 维度："+doublearr[1]);
    }

}
