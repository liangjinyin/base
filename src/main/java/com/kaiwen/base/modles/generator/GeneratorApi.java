package com.kaiwen.base.modles.generator;

import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: liangjinyin
 * @Date: 2019-05-31
 * @Description:
 */
public class GeneratorApi {
    public static void printApi(String name, FileWriter fw, String cname) throws IOException {

        String url = "http://132.121.164.203:18281/sti/logger/list?type=1&sdate=20190522&edate=20190522";
        String urlName = "获取酒店列表";
        String method = "GET";

        fw.write("\n" +
                "    \n" +
                "**简要描述：** \n" +
                "\n" +
                "- " + urlName + "\n" +
                "\n" +
                "**请求URL：** \n" +
                "- `" + url + "`\n" +
                "  \n" +
                "**请求方式：**\n" +
                "- "+method+" \n" +
                "\n" +
                "**参数：** \n" +
                "\n" +
                "|参数名|必选|类型|说明|\n" +
                "|:----    |:---|:----- |-----   |\n"
        );
        String[] urlParms = StringUtils.split(url, "?");
        if (urlParms.length > 1) {
            String urlps = urlParms[1];
            if (StringUtils.contains(urlps, "&")) {
                String[] ps1 = StringUtils.split(urlps, "&");
                for (String s : ps1) {
                    String[] ps4 = StringUtils.split(s, "=");
                    fw.write("|"+ps4[0]+" |否  |string |   例如："+ps4[1]+"|\n");
                }
            }else {
                String[] ps2 = StringUtils.split(urlps, "=");
                fw.write("|"+ps2[0]+" |否  |string |   例如："+ps2[1]+"|\n");
            }
        }
        fw.write("**返回示例**\n");
       /* String s = HttpUtils.get(url);
        fw.write(s);*/
        fw.write("```\n");
        fw.write("\n");
        fw.write("```\n");
        fw.write("**返回参数说明** \n" +
                "\n" +
                "|参数名|类型|说明|\n" +
                "|:-----  |:-----|-----                           |\n" +
                "|groupid |int   |用户组id，1：超级管理员；2：普通用户  |\n");
    }

/*    private void psUtils(String s){
        String[] ps4 = StringUtils.split(s, "=");
        fw.write("|"+ps4[0]+" |否  |string |   例如："+ps4[1]+"|\n");
    }*/
}
