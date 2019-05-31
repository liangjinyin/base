package com.kaiwen.base.modles.generator;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: liangjinyin
 * @Date: 2018-12-11
 * @Description:
 */
public class GeneratorEntity {

    public static void printEntity(String name,FileWriter fw,String cname) throws IOException {

        fw.write("import lombok.Data;\n" +
                "\n" +
                "import javax.persistence.*;\n");
        fw.write("\n");
        fw.write("\n");
        fw.write("/**\n" +
                " * @author: liangjinyin\n" +
                " * @Date: "+ DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"\n" +
                " * @Description:"+cname+" Entity\n" +
                " */\n");

        fw.write("@Data\n" +
                "@Entity\n" +
                "@Table(name = \"\")\n" +
                "public class "+name+" extends BaseEntity implements Serializable {\n");


        //数据库字段
        String str = "     `id` varchar(64) NOT NULL COMMENT '编号',\n" +
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
        fw.write("@Id\n" + "@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
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
                fw.write(String.format(z,m1.group())+"\n");
            }

            //写关联属性 @Column(name = "accese_type")
            String sx = "@Column(name = \"%s\")";
            fw.write(String.format(sx,s2)+"\n");

            //写字段
            if(s.contains("int")){
                fw.write("private Integer "+ss+";"+"\n");
            }else if(s.contains("date")){
                fw.write("private Date "+ss+";"+"\n");
            }else if(s.contains("varchar")){
                fw.write("private String "+ss+";"+"\n");
            }else{
                fw.write("private String "+ss+";"+"\n");
            }
        }
        
        fw.write("}");
    }
}
