package com.kaiwen.base.modles.concurrent;

/**
 * @author: liangjinyin
 * @Date: 2019-03-25
 * @Description: 实现单例模式 懒汉模式
 */
public class Singleton {
    /**
     * 私有化构造函数，不让外部随便创建该对象
     */
    private Singleton() {}

    /**
     * 将对象放到共享内存中 volatile 保证不会发生指令重排
     */
    private volatile static Singleton singleton = null;

    /**
     * 提供外部访问对象 双重锁 提高性能
     */
    public static Singleton getSingleton(){
        if (singleton == null) {
            synchronized (Singleton.class){
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
