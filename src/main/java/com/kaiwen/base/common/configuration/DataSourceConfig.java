package com.kaiwen.base.common.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 配置不同数据源
 */
//@Configuration
public class DataSourceConfig {

    //数据源1-引用配置:lbspDataSource  业务模块数据源 oracle
    @Primary    //配置该数据源为主数据源
    @Bean(name = "lbspDataSource")
    @Qualifier(value = "lbspDataSource")
    @ConfigurationProperties(prefix = "lbsp.spring.datasource") //application.properties配置文件中该数据源的配置前缀
    public DataSource lbspDataSource() {
        return DataSourceBuilder.create().build();
    }


    //数据源2-引用配置lbspmapDataSource 地图模块数据源 postgis
    @Bean(name = "lbspmapDataSource")
    @Qualifier(value = "lbspmapDataSource")
    @ConfigurationProperties(prefix="lbspmap.spring.datasource")
    public DataSource lbspmapDataSource() {
        return DataSourceBuilder.create().build();
    }

}
