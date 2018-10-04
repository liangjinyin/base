package com.kaiwen.base.modles.logger.entity;

import com.kaiwen.base.common.modle.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author: liangjinyin
 * @Date: 2018-09-25
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_log")
public class LoggerInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String url;
    private String params;
    private Integer time;
    @Column(name = "log_time")
    private String logTime;
}
