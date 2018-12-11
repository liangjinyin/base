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
public class GeneratorRepository {

    public static void printRepository(String name, FileWriter fw) throws IOException{
        fw.write("import com.gpdi.keiwen.common.module.repository.BaseRepository;\n");

        fw.write("/**\n" +
                " * @author: liangjinyin\n" +
                " * @Date: "+ DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"\n" +
                " * @Description:\n" +
                " */\n");

        fw.write("public interface "+name+"Repository extends BaseRepository<"+name+", Integer> {\n" +"}");
    }
}
