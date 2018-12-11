package com.kaiwen.base.modles.generator;

/**
 * @author: liangjinyin
 * @Date: 2018-12-11
 * @Description:
 */
public class GeneratorRepository {

    public static void printRepository(String name) {
        System.out.print("public interface "+name+"Repository extends BaseRepository<"+name+", Integer> {\n" +
                "}");
    }
}
