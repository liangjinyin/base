package com.kaiwen.base.modles.lambda;

import com.kaiwen.base.modles.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author: liangjinyin
 * @Date: 2019-05-29
 * @Description: lambda 表达式的练习
 */
@Slf4j
public class LambdaFunction {

    public static void main(String[] args) {
        /**run() 没有入参 也没有返回 void run();*/
        new Thread(() -> log.info("Runnable")).start();
        /** Runnable 有一个入参，有一个返回值 R apply(T t);*/
        Function<String, Integer> a = s -> s.length();
        /** Comparator 排序*/
        Comparator<Customer> c = Comparator.comparing(Customer::getId);
        /** Predicate boolean test(T t);*/
        Predicate<Customer> p = (e -> e.getId().equals(1));
        /** Consumer void accept(T t); 消费者*/
        Consumer<Customer> consumer = e -> log.info(e.getName());
        /** Supplier T get();提供者*/
        Supplier<String> supplier = ()-> {return "a";};

        /** 应用*/

        List<Customer> list = Arrays.asList(new Customer(),new Customer());
        /** Consumer 的应用*/
        list.stream().forEach(e->log.info(e.getName()));

    }
}
