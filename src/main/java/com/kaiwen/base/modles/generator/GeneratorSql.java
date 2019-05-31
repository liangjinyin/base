package com.kaiwen.base.modles.generator;

import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: liangjinyin
 * @Date: 2019-05-30
 * @Description: printSql
 */
public class GeneratorSql {

    public static void printSql(String name, FileWriter fw, String cname) throws IOException {
        fw.write("-  " + cname + "表，储存" + cname + "信息\n" +
                "\n" +
                "|字段|类型|允许空值|默认|注释|\n" +
                "|:----    |:-------    |:--- |-- -|------      |\n"
        );

        //数据库字段
        String str = "      `id` varchar(64) NOT NULL COMMENT '编号',\n" +
                "  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',\n" +
                "  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',\n" +
                "  `name` varchar(100) NOT NULL COMMENT '名称',\n" +
                "  `sort` decimal(10,0) NOT NULL COMMENT '排序',\n" +
                "  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',\n" +
                "  `type` char(1) DEFAULT NULL COMMENT '区域类型',\n" +
                "  `create_by` varchar(64) NOT NULL COMMENT '创建者',\n" +
                "  `create_date` datetime NOT NULL COMMENT '创建时间',\n" +
                "  `update_by` varchar(64) NOT NULL COMMENT '更新者',\n" +
                "  `update_date` datetime NOT NULL COMMENT '更新时间',\n" +
                "  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',\n" +
                "  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',";
        String[] split = str.trim().split("\\\n");
        String a = null;
        String b = null;
        for (String s : split) {
            String[] list = StringUtils.split(s, " ");
            switch (list[2]) {
                case "NOT":
                    a = "否";
                    break;
                default:
                    a = "是";
                    b = "null";
            }
            fw.write("|" + list[0] + " |" + list[1] + "   |" + a + "   |" + b + "  |   " + list[list.length-1] + "  |\n");
        }
    }
}
