package com.kaiwen.base.modles.customer.entity;

import com.kaiwen.base.common.modle.entity.BaseEntity;
import com.kaiwen.base.common.utils.excel.ExcelField;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_customer")
public class Customer extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelField(title = "id", sort = 1)
    private Integer id;
    @NotBlank(message = "客户名称不可为空！")
    @ExcelField(title = "名称", sort = 2)
    private String name;
    @ExcelField(title = "地址", sort = 3)
    private String address;
    @ExcelField(title = "电话", sort = 4)
    @Column(name = "phone")
    private String phone;

    public Customer(@NotBlank(message = "客户名称不可为空！") String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Customer() {
    }
}
