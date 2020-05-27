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
        String str = "  \"gid\" varchar(36) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT uuid_generate_v4(),\n" +
                "  \"mmsi\" varchar(128) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"imo\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"shipname\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"callsign\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"devicetype\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"devicetypestring\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"status\" int4,\n" +
                "  \"statusstring\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"speed\" numeric(50,20),\n" +
                "  \"course\" numeric(50,20),\n" +
                "  \"heading\" numeric(50,20),\n" +
                "  \"longitude\" numeric(50,20),\n" +
                "  \"latitude\" numeric(50,20),\n" +
                "  \"shipwidth\" numeric(50,20),\n" +
                "  \"shiplength\" numeric(50,20),\n" +
                "  \"cargotype\" int4,\n" +
                "  \"cargotypestring\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"expectarrivetime\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"maxdraftdepth\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"positiontype\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"destination\" varchar(64) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"shippeople\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"turnrate\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"turnratestring\" varchar(32) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"receivetime\" timestamptz(4),\n" +
                "  \"lauptime\" timestamptz(4),\n" +
                "  \"geom_cgcs2000\" \"public\".\"geometry\",   ";

        String comment = "COMMENT ON COLUMN \"public\".\"ais_ship\".\"gid\" IS 'id';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"mmsi\" IS '船舶唯一标识';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"imo\" IS 'IMO编号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"shipname\" IS '船舶名称';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"callsign\" IS '呼号';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"devicetype\" IS 'AIS设备类型编码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"devicetypestring\" IS 'AIS设备类型';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"status\" IS '状态编码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"statusstring\" IS '状态';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"speed\" IS '航速';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"course\" IS '对地航向';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"heading\" IS '艏向';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"longitude\" IS '经度';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"latitude\" IS '纬度';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"shipwidth\" IS '船宽';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"shiplength\" IS '船长';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"cargotype\" IS '船货类型编码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"cargotypestring\" IS '船货类型';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"expectarrivetime\" IS '预计到达时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"maxdraftdepth\" IS '最大吃水深度';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"positiontype\" IS '定位类型';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"destination\" IS '目的地';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"shippeople\" IS '船载人数';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"turnrate\" IS '转向率编码';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"turnratestring\" IS '转向率';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"receivetime\" IS '接收时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"lauptime\" IS '更新时间';\n" +
                "\n" +
                "COMMENT ON COLUMN \"public\".\"ais_ship\".\"geom_cgcs2000\" IS '图形2000';";
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
