package com.kaiwen.base.modles.websocket.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
public class Constants {

    //航行状态
    public static Map<String, String> shipStatusMap = new HashMap<>();
    static {
        shipStatusMap.put("0", "在航（引擎）");
        shipStatusMap.put("1", "锚泊");
        shipStatusMap.put("2", "未进行操作");
        shipStatusMap.put("3", "操纵受限");
        shipStatusMap.put("4", "受吃水限制");
        shipStatusMap.put("5", "系泊");
        shipStatusMap.put("6", "搁浅");
        shipStatusMap.put("7", "捕捞作业");
        shipStatusMap.put("8", "在航（船帆）");
        shipStatusMap.put("9", "备用");
        shipStatusMap.put("10", "备用");
        shipStatusMap.put("11", "备用");
        shipStatusMap.put("12", "备用");
        shipStatusMap.put("13", "备用");
        shipStatusMap.put("14", "AIS-SART ACTIVE");
        shipStatusMap.put("15", "AIS-SART TEST");
        }


    //船货类型
    public static Map<String, String> shipTypeMap = new HashMap<>();
    static {
        shipTypeMap.put("0", "未定义");
        shipTypeMap.put("20", "飞翼船");
        shipTypeMap.put("21", "危险飞翼A");
        shipTypeMap.put("22", "危险飞翼B");
        shipTypeMap.put("23", "危险飞翼C");
        shipTypeMap.put("24", "危险飞翼D");
        shipTypeMap.put("30", "渔船");
        shipTypeMap.put("31", "拖带船");
        shipTypeMap.put("32", "大拖带船");
        shipTypeMap.put("33", "水下拖带");
        shipTypeMap.put("34", "潜水拖带");
        shipTypeMap.put("35", "军事拖带");
        shipTypeMap.put("36", "帆船");
        shipTypeMap.put("37", "游艇");
        shipTypeMap.put("40", "高速船舶");
        shipTypeMap.put("41", "危险高速A");
        shipTypeMap.put("42", "危险高速B");
        shipTypeMap.put("43", "危险高速C");
        shipTypeMap.put("44", "危险高速D");
        shipTypeMap.put("50", "领航船");
        shipTypeMap.put("51", "搜救船");
        shipTypeMap.put("52", "拖船");
        shipTypeMap.put("53", "港口供应");
        shipTypeMap.put("54", "防污船舶");
        shipTypeMap.put("55", "执法船");
        shipTypeMap.put("58", "医药运输");
        shipTypeMap.put("59", "非战国船");
        shipTypeMap.put("60", "渡船");
        shipTypeMap.put("61", "危险渡船A");
        shipTypeMap.put("62", "危险渡船B");
        shipTypeMap.put("63", "危险渡船C");
        shipTypeMap.put("64", "危险渡船D");
        shipTypeMap.put("70", "货船");
        shipTypeMap.put("71", "危险货船A");
        shipTypeMap.put("72", "危险货船B");
        shipTypeMap.put("73", "危险货船C");
        shipTypeMap.put("74", "危险货船D");
        shipTypeMap.put("80", "油船");
        shipTypeMap.put("81", "危险油船A");
        shipTypeMap.put("82", "危险油船B");
        shipTypeMap.put("83", "危险油船C");
        shipTypeMap.put("84", "危险油船D");
        shipTypeMap.put("90", "其它");
    }


    //AIS设备类型
    public static Map<String, String> aisStatusMap = new HashMap<>();
    static {
        aisStatusMap.put("0", "CLASS-A");
        aisStatusMap.put("1", "CLASS-B");
        aisStatusMap.put("2", "基站");
        aisStatusMap.put("3", "航标");
        aisStatusMap.put("4", "搜救直升机");
        aisStatusMap.put("5", "SART搜救应答器");
    }


    // CORS 高精度用户 GGA的最新解算状态
    public static Map<String, String> corsGgaMap = new HashMap<>();
    static {
        corsGgaMap.put("0", "无效解");
        corsGgaMap.put("1", "单点解");
        corsGgaMap.put("2", "差分3D");
        corsGgaMap.put("3", "PPP");
        corsGgaMap.put("4", "固定解");
        corsGgaMap.put("5", "浮点解");
        corsGgaMap.put("6", "差分WASS");
        corsGgaMap.put("7", "基站");
    }


}
