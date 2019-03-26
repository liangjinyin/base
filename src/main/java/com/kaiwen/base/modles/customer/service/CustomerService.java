package com.kaiwen.base.modles.customer.service;


import com.alibaba.fastjson.JSON;
import com.kaiwen.base.common.modle.entity.PageQuery;
import com.kaiwen.base.common.modle.service.CrudService;
import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.modles.customer.dao.CustomerDao;
import com.kaiwen.base.modles.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CustomerService extends CrudService<CustomerDao, Customer> {


    public Object findAllPageAndQuery(Pageable pageable, PageQuery pageQuery) {
       /* return super.dao.findAll(
                (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"),pageQuery.getName())
        );*/
        try {
            Specification specification = new Specification() {
                @Nullable
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                    Path name = root.get("name");
                    cb.equal(name, pageQuery.getName());
                    return cq.where().getRestriction();
                }
            };
            Page<Customer> page = super.dao.findAll(specification, pageable);
            data.put("page", page);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    public Object saveAllCustomer(List<Customer> customers) {
        try {
            super.dao.saveAll(customers);
            return ResultCode.OPERATION_SUCCESSED;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    public Object chaceCustomer() {
        ForkJoinPool pool = null;
        try {
            //582 333 618 412
            list = super.dao.findAll();
            Cache cache = CacheManager.create().getCache("customer");
//            List<Element> collect = list.stream().map(e -> {
//                Element element = new Element(e.getId(), e.toString());
//                return element;e
//            }).collect(Collectors.toList());
//            cache.putAll(collect);
            // 多个线程执行任务 使用countDownLatch
            CountDownLatch countDownLatch = new CountDownLatch(list.size());
            pool = new ForkJoinPool(20);
            pool.submit(() -> {
                list.stream().parallel().forEach(e -> cache.put(new Element(e.getId(), JSON.toJSONString(e))));
                countDownLatch.countDown();
            });
            countDownLatch.await();
            return ResultCode.OPERATION_SUCCESSED;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        } finally {
            pool.shutdown();
        }
    }

    public Object findDate(String name) {
        try {
            list = super.dao.findDate(name);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }
}
