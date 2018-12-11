package com.kaiwen.base.modles.generator;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author: liangjinyin
 * @Date: 2018-12-11
 * @Description:
 */
public class GeneratorController {

    public static void printController(String name,FileWriter fw) throws IOException {

        fw.write("import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.*;\n");

        fw.write("/**\n" +
                " * @author: liangjinyin\n" +
                " * @Date: "+ DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"\n" +
                " * @Description:\n" +
                " */\n");

        fw.write("" +
                "@RestController\n" +
                "@RequestMapping(value = \"/"+name.toLowerCase()+"\",produces = \"application/json; charset=utf-8\")\n" +
                "public class "+name+"Controller extends BaseController{\n" +
                "    @Autowired\n" +
                "    private "+name+"Service "+name.toLowerCase()+"Service;\n" +
                "\n" +
                "    @GetMapping(\"/findOne\")\n" +
                "    public String find"+name+"ById(Integer id){\n" +
                "        data = "+name.toLowerCase()+"Service.find"+name+"ById(id);\n" +
                "        return result();\n" +
                "    }\n" +
                "\n" +
                "    @GetMapping(\"/findList\")\n" +
                "    public String findAll"+name+"(MyPage pageable, String key){\n" +
                "        data = "+name.toLowerCase()+"Service.findPage(pageable,key);\n" +
                "        return result();\n" +
                "    }\n" +
                "\n" +
                "    @PostMapping(\"/saveOrUpdate\")\n" +
                "    public String saveOrUpdate"+name+"(@RequestBody "+name+" "+name+"){\n" +
                "        data = "+name.toLowerCase()+"Service.save"+name+"("+name+");\n" +
                "        return result();\n" +
                "    }\n" +
                "\n" +
                "    @GetMapping(\"/delete\")\n" +
                "    public String delete"+name+"ById(Integer id){\n" +
                "        data = "+name.toLowerCase()+"Service.delete"+name+"ById(id);\n" +
                "        return result();\n" +
                "    }" );
        fw.write("}");
    }
}
