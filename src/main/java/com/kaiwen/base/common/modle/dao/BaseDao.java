package com.kaiwen.base.common.modle.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author: liangjinyin
 * @Date: 2018-09-21
 * @Description: Dao基类
 */
@NoRepositoryBean
public interface BaseDao<T,ID> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {

    @Override
    <S extends T> List<S> saveAll(Iterable<S> iterable);
}
