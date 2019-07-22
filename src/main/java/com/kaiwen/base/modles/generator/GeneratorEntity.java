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
        String str = "   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',\n" +
                "  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',\n" +
                "  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',\n" +
                "  `imms` varchar(255) DEFAULT NULL COMMENT '船舶唯一id',\n" +
                "  `data` varchar(255) DEFAULT NULL COMMENT '时间',";
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
