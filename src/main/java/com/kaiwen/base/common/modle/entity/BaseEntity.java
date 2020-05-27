package com.kaiwen.base.common.modle.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description:
 */
@Data
@MappedSuperclass // 基础类，可
// @EqualsAndHashCode(callSuper=false)
public abstract class BaseEntity implements Serializable{
    protected Integer flag;
    protected Integer id;
}
