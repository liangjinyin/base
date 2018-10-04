package com.kaiwen.base.modles.customer.controller;


import com.kaiwen.base.common.modle.controller.BaseController;
import com.kaiwen.base.common.modle.entity.PageQuery;
import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.common.utils.excel.ExportExcel;
import com.kaiwen.base.modles.customer.entity.Customer;
import com.kaiwen.base.modles.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController extends BaseController {


    @Autowired
    private CustomerService customerService;


    @GetMapping("/findList")
    public String findCustomerList() {
        data = customerService.findAll();
        return result();
    }
    @GetMapping("/ehchace")
    public String chaceCustomer() {
        data = customerService.chaceCustomer();
        return result();
    }

    @PostMapping("/save")
    public String saveCustomer(@Validated Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            setErrorResultCode(bindingResult);
        } else {
            data = customerService.saveOrUpdateEntity(customer);
        }
        return result();
    }

    @GetMapping("/allSave")
    public String saveAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            Customer c = new Customer("冰龙" + i, "地球区" + i, "18888344745");
            customers.add(c);
        }
        data = customerService.saveAllCustomer(customers);
        return result();
    }



    @GetMapping("/findPage1")
    public String findCustomerPage(Pageable pageable, PageQuery pageQuery) {
        data = customerService.findAllPageAndQuery(pageable, pageQuery);
        return result();
    }

    @Cacheable(value = "customer", key = "'customer'")
    @GetMapping("/findPage")
    public String findPage(Pageable pageable) {
        data = customerService.findAllPage(pageable);
        return result();
    }
    @Cacheable(value = "customer", key = "#id")
    @GetMapping("/find/{id}")
    public String findById(@PathVariable("id") Integer id) {
        data = customerService.findEntityById(id);
        return result();
    }
    @GetMapping("/data")
    public String findData() {
        data = "haha";
        return result();
    }


    @GetMapping("/export")
    public String exportCustomer(HttpServletResponse response) {

        try {
            String fileName = "客户" + "2" + ".xlsx";
            data = customerService.findAll();
            if (data instanceof ResultCode) {
                resCode = (ResultCode) data;
                data = null;
            } else {
                List<Customer> customerList = (List<Customer>) data;
                new ExportExcel("客户", Customer.class).setDataList(customerList).write(response, fileName).dispose();
            }
        } catch (Exception e) {
            log.info("导出数据失败！原因：{}", e.getMessage());
            resCode = ResultCode.OPERATION_FAILED;
            e.printStackTrace();
        }
        return result();
    }
}
