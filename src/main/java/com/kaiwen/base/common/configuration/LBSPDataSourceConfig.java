package com.kaiwen.base.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务数据源：使用DataSource注入JPA,指明每个数据源SpringData要扫描的Dao的包名
 */
//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryLBSP",
        transactionManagerRef="transactionManagerLBSP",
        basePackages= {"com.southsmart.lbsp.lbsp.entity", "com.southsmart.lbsp.systemsafety.entity",
                "com.southsmart.lbsp.lbsp.dao", "com.southsmart.lbsp.systemsafety.dao"}) //设置Repository所在位置
public class LBSPDataSourceConfig {


    @Autowired
    private JpaProperties jpaProperties;


    //注入数据源 lbspDataSource - 引用数据源1配置:lbspDataSource
    @Resource(name = "lbspDataSource")
    private DataSource lbspDataSource;


    @Value("${lbsp.spring.jpa.properties.hibernate.dialect}")
    private String lbspDialect;// 获取对应的数据库方言


    /**
     * 创建数据源1的entityManagerFactoryLBSP
     * 1,使用lbspDataSource配置
     * 2,数据实体包名packages:com.southsmart.lbsp.lbsp
     */
    @Primary
    @Bean(name = "entityManagerFactoryLBSP")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryLBSP(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(lbspDataSource) //设置数据源
                .properties(getVendorProperties()) //设置数据源属性
                .packages("com.southsmart.lbsp.lbsp", "com.southsmart.lbsp.systemsafety") //设置实体类所在位置 扫描所有带有 @Entity 注解的类
                .persistenceUnit("lbspPersistenceUnit")
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        Map<String,String> map = new HashMap<>();
        map.put("hibernate.dialect", lbspDialect);// 设置对应的数据库方言
        jpaProperties.setProperties(map);
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }


    /**
     * 配置事物管理器
     * @param builder
     * @return
     */
    @Primary
    @Bean(name = "transactionManagerLBSP")
    public PlatformTransactionManager transactionManagerLBSP(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryLBSP(builder).getObject());
    }


    /**
     * 配置EntityManager实体
     * @param builder
     * @return
     */
    @Primary
    @Bean(name = "entityManagerLBSP")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryLBSP(builder).getObject().createEntityManager();
    }


}
