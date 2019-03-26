package com.kaiwen.base.modles.customer.dao;

import com.kaiwen.base.common.modle.dao.BaseDao;
import com.kaiwen.base.modles.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description:
 */

public interface CustomerDao extends BaseDao<Customer, Integer> {



    List<Customer> findCustomersByIdIn(List<Integer> idList);

    Page<Customer> findByNameLikeAndAddressLikeOrderByIdDesc(String name, String address, Pageable pageable);

    // @Modifying update
    /*@Query( value = "SELECT * FROM sys_customer WHERE 1=1 " +
            " AND if(:name!='',name LIKE CONCAT('%',:name,'%'),1=1)",
            nativeQuery = true)*/
    @Query(value = "SELECT * FROM sys_customer WHERE 1=1 "+
            " AND (name LIKE CONCAT('%',:name,'%') OR :name IS NULL )",
            nativeQuery = true)
    List<Customer> findDate(@Param("name") String name);
}
