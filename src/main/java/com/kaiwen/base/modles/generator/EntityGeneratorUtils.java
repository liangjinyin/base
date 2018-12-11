package com.kaiwen.base.modles.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: liangjinyin
 * @Date: 2018-10-31
 * @Description: 代码生成器
 */
public class EntityGeneratorUtils {

    public static void main(String[] args) {
        printMy("");
    }

    private static void printMy(String name) {
        GeneratorEntity.printEntity(name);
        GeneratorService.printService(name);
        GeneratorRepository.printRepository(name);
        GeneratorController.printController(name);
    }

    private static void write(String name) {
        File file = null;
        FileWriter fw = null;
        file = new File("G:\\code\\"+name+".java");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write("");
            fw.flush();

            System.out.println("写数据成功！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
