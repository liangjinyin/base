package com.kaiwen.base.common.utils;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Rectangle;
import java.util.Arrays;

/**
 * @author: liangjinyin
 * @Date: 2019-09-26
 * @Description:
 */

public class MyGeohashUtils {

    private static final char[] BASE_32 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int[] BASE_32_IDX;
    public static final int MAX_PRECISION = 24;
    private static final int[] BITS = new int[]{16, 8, 4, 2, 1};
    private static final double[] hashLenToLatHeight;
    private static final double[] hashLenToLonWidth;

    private MyGeohashUtils() {
    }

    public static String encodeLatLon(double latitude, double longitude) {
        return encodeLatLon(latitude, longitude, 12);
    }
    // 头部：121.96473538190111, 31.683295381234334 尾部：104.6145587852267, 28.759875901599752
    public static String encodeLatLon(double latitude, double longitude, int precision) {
        double[] latInterval = new double[]{26.70D, 32.7D};
        double[] lngInterval = new double[]{104.61D, 122.0D};
        StringBuilder geohash = new StringBuilder(precision);
        boolean isEven = true;
        int bit = 0;
        int ch = 0;

        while(geohash.length() < precision) {
            double mid = 0.0D;
            if (isEven) {
                mid = (lngInterval[0] + lngInterval[1]) / 2.0D;
                if (longitude > mid) {
                    ch |= BITS[bit];
                    lngInterval[0] = mid;
                } else {
                    lngInterval[1] = mid;
                }
            } else {
                mid = (latInterval[0] + latInterval[1]) / 2.0D;
                if (latitude > mid) {
                    ch |= BITS[bit];
                    latInterval[0] = mid;
                } else {
                    latInterval[1] = mid;
                }
            }

            isEven = !isEven;
            if (bit < 4) {
                ++bit;
            } else {
                geohash.append(BASE_32[ch]);
                bit = 0;
                ch = 0;
            }
        }

        return geohash.toString();
    }

    public static Point decode(String geohash, SpatialContext ctx) {
        Rectangle rect = decodeBoundary(geohash, ctx);
        double latitude = (rect.getMinY() + rect.getMaxY()) / 2.0D;
        double longitude = (rect.getMinX() + rect.getMaxX()) / 2.0D;
        return ctx.makePoint(longitude, latitude);
    }

    // 28.70D, 31.7D 104.61D, 122.0D
    public static Rectangle decodeBoundary(String geohash, SpatialContext ctx) {
        double minY = 26.70D;
        double maxY = 32.7D;
        double minX = 104.61D;
        double maxX = 122.0D;
        boolean isEven = true;

        for(int i = 0; i < geohash.length(); ++i) {
            char c = geohash.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char)(c + 32);
            }

            int cd = BASE_32_IDX[c - BASE_32[0]];
            int[] var14 = BITS;
            int var15 = var14.length;

            for(int var16 = 0; var16 < var15; ++var16) {
                int mask = var14[var16];
                if (isEven) {
                    if ((cd & mask) != 0) {
                        minX = (minX + maxX) / 2.0D;
                    } else {
                        maxX = (minX + maxX) / 2.0D;
                    }
                } else if ((cd & mask) != 0) {
                    minY = (minY + maxY) / 2.0D;
                } else {
                    maxY = (minY + maxY) / 2.0D;
                }

                isEven = !isEven;
            }
        }

        return ctx.makeRectangle(minX, maxX, minY, maxY);
    }

    public static String[] getSubGeohashes(String baseGeohash) {
        String[] hashes = new String[BASE_32.length];

        for(int i = 0; i < BASE_32.length; ++i) {
            char c = BASE_32[i];
            hashes[i] = baseGeohash + c;
        }

        return hashes;
    }

    public static double[] lookupDegreesSizeForHashLen(int hashLen) {
        return new double[]{hashLenToLatHeight[hashLen], hashLenToLonWidth[hashLen]};
    }

    public static int lookupHashLenForWidthHeight(double lonErr, double latErr) {
        for(int len = 1; len < 24; ++len) {
            double latHeight = hashLenToLatHeight[len];
            double lonWidth = hashLenToLonWidth[len];
            if (latHeight < latErr && lonWidth < lonErr) {
                return len;
            }
        }

        return 24;
    }

    static {
        BASE_32_IDX = new int[BASE_32[BASE_32.length - 1] - BASE_32[0] + 1];

        assert BASE_32_IDX.length < 100;

        Arrays.fill(BASE_32_IDX, -500);

        for(int i = 0; i < BASE_32.length; BASE_32_IDX[BASE_32[i] - BASE_32[0]] = i++) {
        }

        hashLenToLatHeight = new double[25];
        hashLenToLonWidth = new double[25];
        hashLenToLatHeight[0] = 180.0D;
        hashLenToLonWidth[0] = 360.0D;
        boolean even = false;

        for(int i = 1; i <= 24; ++i) {
            hashLenToLatHeight[i] = hashLenToLatHeight[i - 1] / (double)(even ? 8 : 4);
            hashLenToLonWidth[i] = hashLenToLonWidth[i - 1] / (double)(even ? 4 : 8);
            even = !even;
        }

    }
}

