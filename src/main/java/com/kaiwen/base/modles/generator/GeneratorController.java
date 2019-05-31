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

    public static void printController(String name,FileWriter fw,String cname) throws IOException {

        fw.write("import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.*;\n");

        fw.write("/**\n" +
                " * @author: liangjinyin\n" +
                " * @Date: "+ DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"\n" +
                " * @Description:"+cname+" Controller\n" +
                " */\n");

        fw.write("" +
                "@RestController\n" +
                "@RequestMapping(value = \"/"+name.toLowerCase()+"\",produces = \"application/json; charset=utf-8\")\n" +
                "public class "+name+"Controller extends BaseController{\n" +
                "    @Autowired\n" +
                "    private "+name+"Service "+name.toLowerCase()+"Service;\n" +
                "\n" +
                "     /**\n" +
                "     * 根据id获取"+cname+"\n" +
                "     * @param id "+cname+"id\n" +
                "     * @return "+cname+"实体类\n" +
                "     */\n"+
                "    @GetMapping(\"/findOne\")\n" +
                "    public String find"+name+"ById(Integer id){\n" +
                "        data = "+name.toLowerCase()+"Service.find"+name+"ById(id);\n" +
                "        return result();\n" +
                "    }\n" +
                "\n" +
                "     /**\n" +
                "     * 根据条件获取"+cname+"list\n" +
                "     * @param pageable 页码条件\n" +
                "     * @return "+cname+"实体类list\n" +
                "     */\n"+
                "    @GetMapping(\"/findList\")\n" +
                "    public String findAll"+name+"(MyPage pageable, String key){\n" +
                "        data = "+name.toLowerCase()+"Service.findPage(pageable,key);\n" +
                "        return result();\n" +
                "    }\n" +
                "\n" +
                "     /**\n" +
                "     * 新增或者修改"+cname+"\n" +
                "     * @param "+name +cname+" \n" +
                "     * @return "+cname+"实体类\n" +
                "     */\n"+
                "    @PostMapping(\"/saveOrUpdate\")\n" +
                "    public String saveOrUpdate"+name+"(@RequestBody "+name+" "+name.toLowerCase()+"){\n" +
                "        data = "+name.toLowerCase()+"Service.save"+name+"("+name.toLowerCase()+");\n" +
                "        return result();\n" +
                "    }\n" +
                "\n" +
                "     /**\n" +
                "     * 根据id删除"+cname+"\n" +
                "     * @param id "+cname+"id\n" +
                "     * @return "+cname+"实体类\n" +
                "     */\n"+
                "    @GetMapping(\"/delete\")\n" +
                "    public String delete"+name+"ById(Integer id){\n" +
                "        data = "+name.toLowerCase()+"Service.delete"+name+"ById(id);\n" +
                "        return result();\n" +
                "    }\n" +
                "}" );

    }
}
