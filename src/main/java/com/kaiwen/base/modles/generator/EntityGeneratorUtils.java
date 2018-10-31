package com.kaiwen.base.modles.generator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: liangjinyin
 * @Date: 2018-10-31
 * @Description: 代码生成器
 */
public class EntityGeneratorUtils {

    public static void main(String[] args) {
        String str = "   `room_id` int(16) DEFAULT NULL COMMENT '房间id',\n" +
                "  `room_name` varchar(64) DEFAULT NULL COMMENT '房间号',\n" +
                "  `build_id` int(16) DEFAULT NULL COMMENT '建筑id',\n" +
                "  `floor_id` int(16) DEFAULT NULL COMMENT '归属楼层id',\n" +
                "  `hotel_id` int(16) DEFAULT NULL COMMENT '酒店id,  非酒店房间置空',\n" +
                "  `full_name` varchar(64) DEFAULT NULL COMMENT '房间全名称',\n" +
                "  `cust_id` int(16) DEFAULT NULL COMMENT '客户id',\n" +
                "  `net_type` varchar(32) DEFAULT NULL COMMENT '网络结构',\n" +
                "  `access_type` varchar(32) DEFAULT NULL COMMENT '接入方式',\n" +
                "  `registe_flag` varchar(4) DEFAULT NULL COMMENT '是否已登记',\n" +
                "  `equipment_id` int(16) DEFAULT NULL COMMENT '资源设备ID',\n" +
                "  `is_ctcc` varchar(1) DEFAULT NULL COMMENT '移动业务标识',\n" +
                "  `is_unicom` varchar(1) DEFAULT NULL COMMENT '联通业务标识',\n" +
                "  `is_chinatel` varchar(1) DEFAULT NULL COMMENT '电信业务标识',\n" +
                "  `no_business` varchar(1) DEFAULT NULL COMMENT '无业务',\n" +
                "  `kd_mark` varchar(1) DEFAULT NULL COMMENT '宽带标识',\n" +
                "  `zxhl_mark` varchar(1) DEFAULT NULL COMMENT '专线互联标识',\n" +
                "  `gh_mark` varchar(1) DEFAULT NULL COMMENT '固话标识',\n" +
                "  `itv_mark` varchar(1) DEFAULT NULL COMMENT 'ITV标识',\n" +
                "  `mobile_mark` varchar(1) DEFAULT NULL COMMENT '移动标识',\n" +
                "  `bf_num` int(4) DEFAULT NULL COMMENT '拜访次数',\n" +
                "  `gz_num` int(4) DEFAULT NULL COMMENT '故障次数',\n" +
                "  `ts_num` int(4) DEFAULT NULL COMMENT '投诉次数',";

        String[] split = str.trim().split("\\\n");
        for (String s : split) {
            String s1 = s.trim().substring(1, s.trim().length() );
            String s2 = s1.substring(0, s1.indexOf("`"));

            String ss  = null;
            if(s2.indexOf("_")>0){
                String[] s3 = s2.split("_");
                ss = s3[0];
                for (int i = 1; i < s3.length; i++) {
                    ss = ss.concat(s3[i].substring(0,1).toUpperCase().concat(s3[i].substring(1)));
                }
            }else{
                ss = s2;
            }
            //写注释
            String pp1 = " '([^']*)'";
            Pattern p1 = Pattern.compile(pp1);
            Matcher m1 = p1.matcher(s);
            if (m1.find()) {
                String z = "/** %s */";
                System.out.println(String.format(z,m1.group()));
            }

            //写关联属性 @Column(name = "accese_type")
            String sx = "@Column(name = \"%s\")";
            System.out.println(String.format(sx,s2));
            
            //写字段
            if(s.contains("int")){
                System.out.println("private Integer "+ss+";");
            }else if(s.contains("date")){
                System.out.println("private Date "+ss+";");
            }else if(s.contains("varchar")){
                System.out.println("private String "+ss+";");
            }
        }
    }
}
