package com.kaiwen.base.modles.generator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: liangjinyin
 * @Date: 2018-12-11
 * @Description:
 */
public class GeneratorEntity {

    public static void printEntity(String name){

        System.out.print("@Data\n" +
                "@Entity\n" +
                "@Table(name = \"\")\n" +
                "public class "+name+" extends BaseEntity implements Serializable {\n");


        //数据库字段
        String str = "   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id 主键',\n" +
                "  `rank_id` varchar(32) DEFAULT NULL COMMENT '等级id',\n" +
                "  `name` varchar(64) DEFAULT NULL COMMENT '等级名称',\n" +
                "  `code` varchar(64) DEFAULT NULL COMMENT '等级编码',\n" +
                "  `class_code` varchar(64) DEFAULT NULL COMMENT '类编码',\n" +
                "  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级id',\n" +
                "  `grade` tinyint(8) DEFAULT NULL COMMENT '等级',\n" +
                "  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',";

        String[] split = str.trim().split("\\\n");
        System.out.print("@Id\n" + "@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
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
            }else{
                System.out.println("private String "+ss+";");
            }
        }
        
        System.out.print("}");
    }
}
