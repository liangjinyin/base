package com.kaiwen.base.modles.lambda;

import com.kaiwen.base.modles.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
        Supplier<String> supplier = () -> {
            return "a";
        };

        /** 应用*/

        List<Customer> list = Arrays.asList(new Customer(), new Customer());
        /** Consumer 的应用*/
        list.stream().forEach(e -> log.info(e.getName()));
        list.stream().filter(e->e.getId()>1)
                .sorted(Comparator.comparing(Customer::getId))
                .map(Customer::getName)
                .collect(toList());
        // 多线程执行
        list.parallelStream().filter(e->e.getId()>1)
                .sorted(Comparator.comparing(Customer::getId))
                .map(Customer::getName)
                .collect(toList());
        // 去重 截取最后5元素 获取前面3个元素
        list.stream().distinct().skip(5).limit(3).collect(toList());

        // optional
        Optional<Customer> first = list.stream().findFirst();
        // 获取不到返回填入的值
        Customer customer = first.orElse(null);
        // 若是存在则进行下一步操作
        first.ifPresent(e->log.info(e.getName()));
        // reduce 接收的是一个消费者
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // reduce (初始值，两个入参的消费者)
        // Integer reduce = stream.reduce(0, (u, b) -> u + b);
        Integer reduce = stream.reduce(0,Integer::max);
        stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.reduce((u, b) -> u + b).ifPresent(System.out::print);
        stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.reduce((u, b) -> u + b).ifPresent(System.out::print);

    }
}
