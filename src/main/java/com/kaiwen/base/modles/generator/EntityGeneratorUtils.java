package com.kaiwen.base.modles.generator;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: liangjinyin
 * @Date: 2018-10-31
 * @Description: 代码生成器
 */
@Slf4j
public class EntityGeneratorUtils {

    public static void main(String[] args) {
        printMy("Resource","资源");
    }

    /**
     * {@link EntityGeneratorUtils}.
     * @param name 英文名称
     * @param cname 中文名称
     */
    private static void printMy(String name,String cname) {
        /*write(name, "Entity",cname);
        write(name, "Service",cname);
        write(name, "Repository",cname);
        write(name, "Controller",cname);*/
        write(name, "SQL",cname);
    }

    private static void write(String name, String fileName,String cname) {
        File file = null;
        FileWriter fw = null;
        if ("Entity".equals(fileName)) {
            file = new File("G:\\code\\" + fileName.toLowerCase() + "\\" + name  + ".java");
        }else if ("SQL".equals(fileName)){
            file = new File("G:\\code\\" + fileName.toLowerCase() + "\\" + name  + ".sql");
        } else {
            file = new File("G:\\code\\" + fileName.toLowerCase() + "\\" + name + fileName + ".java");
        }
        log.info(file.getPath());
        try {

            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            file.createNewFile();
            fw = new FileWriter(file);
            switch (fileName) {
                case "Entity":
                    GeneratorEntity.printEntity(name, fw,cname);
                    break;
                case "Service":
                    GeneratorService.printService(name, fw,cname);
                    break;
                case "Repository":
                    GeneratorRepository.printRepository(name, fw,cname);
                    break;
                case "Controller":
                    GeneratorController.printController(name, fw,cname);
                    break;
                case "SQL":
                    GeneratorSql.printSql(name, fw,cname);
                    break;
                default:
                    break;
            }
            fw.flush();
            log.info("写数据成功！");
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
