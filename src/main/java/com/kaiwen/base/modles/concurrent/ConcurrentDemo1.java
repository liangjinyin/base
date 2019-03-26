package com.kaiwen.base.modles.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: liangjinyin
 * @Date: 2019-03-20
 * @Description: 高并发编程1 高并发测试类
 */
@Slf4j
public class ConcurrentDemo1 {
    /**
     * 定义一个线程数 和 用户访问总次数
     */
    private static Integer threadNum = 200;
    private static Integer clientNum = 5000;
    private static Integer count = 0;
    //private static Map map = new HashMap<Integer, Integer>(); 线程不安全
    private static Map<Integer, Integer> map = new ConcurrentHashMap<>(16);//线程安全？

    public static void main(String[] args) throws Exception {
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 声明计数信号量为200 ，
        final Semaphore semaphore = new Semaphore(threadNum);
        for (int i = 0; i < clientNum; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    // 线程池中一次只能发起200个请求 从此信号量获取一个许可前线程将一直阻塞
                    semaphore.acquire();
                    //func(threadNum);
                    add();
                    // 释放一个许可，将其返回给信号量
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 关闭线程池
        executorService.shutdown();
        //log.info("执行完了，map的size是：{}", map.size());
        // 执行完了，map的size是：4854
        log.info("执行完毕，count=:{}",count);
        // 执行完毕，count=:4720
    }

    private static void add() {
        count++;
    }

    private static void func(int threadNum) {
        map.put(threadNum, threadNum);
    }
}
