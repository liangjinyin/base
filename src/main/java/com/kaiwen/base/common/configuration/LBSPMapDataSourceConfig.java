package com.kaiwen.base.common.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryLBSPMap",
        transactionManagerRef="transactionManagerLBSPMap",
        basePackages= {"com.southsmart.lbsp.lbspmap.entity", "com.southsmart.lbsp.lbspmap.dao"}) //设置Repository所在位置
public class LBSPMapDataSourceConfig {

    //注入数据源
    @Resource(name = "lbspmapDataSource")
    private DataSource lbspmapDataSource;

    //注入JPA配置实体
    @Autowired
    private JpaProperties jpaProperties;

    @Value("${lbspmap.spring.jpa.properties.hibernate.dialect}")
    private String lbspmapDialect;// 获取对应的数据库方言


    /**
     * 根据当前数据源配置,创建entityManagerFactory
     * @param builder
     * @return
     */
    @Bean(name = "entityManagerFactoryLBSPMap")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryLBSPMap (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(lbspmapDataSource)
                .properties(getVendorProperties())
                .packages("com.southsmart.lbsp.lbspmap") //设置实体类所在位置
                .persistenceUnit("lbspmapPersistenceUnit")
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        Map<String,String> map = new HashMap<>();
        map.put("hibernate.dialect", lbspmapDialect);// 设置对应的数据库方言
        jpaProperties.setProperties(map);
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }


    /**
     * 根据entityManagerFactory(entityManagerFactoryLBSPMap)生成transactionManager(transactionManagerLBSPMap)
     * @param builder PlatformTransactionManager
     * @return
     */
    @Bean(name = "transactionManagerLBSPMap")
    PlatformTransactionManager transactionManagerLBSPMap(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryLBSPMap(builder).getObject());
    }


    /**
     * 创建 EntityManager :
     *      根据EntityManagerFactoryBuilder 创建 EntityManager
     * @param builder EntityManagerFactoryBuilder
     * @return
     */
    @Bean(name = "entityManagerLBSPMap")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryLBSPMap(builder).getObject().createEntityManager();
    }

}
