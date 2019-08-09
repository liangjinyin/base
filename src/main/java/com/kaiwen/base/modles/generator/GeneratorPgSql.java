package com.kaiwen.base.modles.generator;

import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: liangjinyin
 * @Date: 2019-08-08
 * @Description:
 */
public class GeneratorPgSql {
    public static void printSql(String name, FileWriter fw, String cname) throws IOException {
        fw.write("-  " + cname + "表，储存" + cname + "信息\n" +
                "\n" +
                "|字段|类型|允许空值|默认|注释|\n" +
                "|:----    |:-------    |:--- |-- -|------      |\n"
        );

        //数据库字段
        String str = "      \"id\" varchar(64) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"geom_cgcs2000\" \"public\".\"geometry\",\n" +
                "  \"gwonlinestatus\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"latitude\" float8,\n" +
                "  \"lauptime\" timestamp(6),\n" +
                "  \"longitude\" float8,\n" +
                "  \"stationid\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"stationname\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"stationtype\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"userid\" varchar(255) COLLATE \"pg_catalog\".\"default\",";

        String comment = " COMMENT ON COLUMN \"public\".\"base_station\".\"id\" IS 'id';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"geom_cgcs2000\" IS '图形2000';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"gwonlinestatus\" IS '站点状态（0离线 1在线）';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"latitude\" IS '纬度';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"lauptime\" IS '操作时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"longitude\" IS '经度';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"stationid\" IS '站点ID';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"stationname\" IS '站点名称';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"stationtype\" IS '站点类型：01--CORS基站 / 02--AIS基站';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"base_station\".\"userid\" IS '操作人id';";
        comment = comment.replaceAll("/r|/n", "");

        String[] split1 = comment.trim().split("\\\n");
        String[] split = str.trim().split("\\\n");
        String a = null;
        String b = null;
        for (int i = 0; i < split.length; i++) {
            int is = split1[i*2].indexOf("IS") + 2;
            String sss = split1[i * 2];
            String substring = sss.substring(is, sss.length() - 1);
            String[] list = StringUtils.split(split[i], " ");
            fw.write("|" + list[0] + " |" + list[1] + "   |" + "否" + "   |" + b + "  |   " + substring + "  |\n");
        }
    }
}
