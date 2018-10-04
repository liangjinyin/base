package com.kaiwen.base.modles.customer.dao;

import com.kaiwen.base.common.modle.dao.BaseDao;
import com.kaiwen.base.modles.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description:
 */

public interface CustomerDao extends BaseDao<Customer,Integer> {

    List<Customer> findCustomersByIdIn(List<Integer> idList);

    Page<Customer> findByNameLikeAndAddressLikeOrderByIdDesc(String name, String address, Pageable pageable);

}
