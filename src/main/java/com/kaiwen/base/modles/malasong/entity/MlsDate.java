package com.kaiwen.base.modles.malasong.entity;

import com.kaiwen.base.common.modle.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author: liangjinyin
 * @Date: 2018-12-27
 * @Description:
 */
@Data
@Entity
@Table(name = "hy_marathon")
public class MlsDate extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
/**  '统计时间' */
    @Column(name = "ewd_time")
    private String ewdTime;
    /**
     * '分析维度'
     */
    @Column(name = "big_class")
    private String bigClass;
    @Column(name = "mid_class")
    private String midClass;
    @Column(name = "ind_class")
    private String indClass;
    /**
     * '统计结果'
     */
    @Column(name = "sta_results")
    private String staResults;
    /**
     * '分类(15MIN:15分钟;1H:1小时)'
     */
    @Column(name = "type")
    private String type;
    @Column(name = "state")
    private String state;
}