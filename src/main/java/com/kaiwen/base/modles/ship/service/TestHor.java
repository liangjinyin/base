package com.kaiwen.base.modles.ship.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: liangjinyin
 * @Date: 2019-08-01
 * @Description:
 */
public class TestHor {

    public static void main(String[] args) throws ParseException {
        String time = "2011-05-12 25:02:30";
        // 12小时制
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 24小时制
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sdf1.parse(time);
        Date d2 = sdf2.parse(time);
        System.out.println(d1.toString());
        // 重新format却是正确的
        System.out.println(sdf1.format(d1).toString());
        System.out.println(d2.toString());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.get(Calendar.HOUR);
        System.out.println(c.getTime().toString());
        c.get(Calendar.HOUR_OF_DAY);
        System.out.println(c.getTime().toString());

        String s = add8Hours("20190731205129");
        System.out.println("=============");
        System.out.println(s);
    }
    // 日期时间 加8小时
    private static String add8Hours(String YMDHMS){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = format.parse(YMDHMS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, 8);// 24小时制 + 8小时：中国是东八区
            //cal.get(Calendar.HOUR_OF_DAY);
            date = cal.getTime();
            return  format.format(date);
        }
        return null;
    }
}
