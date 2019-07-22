package com.kaiwen.base.common.modle.service;

import com.kaiwen.base.common.modle.dao.BaseDao;
import com.kaiwen.base.common.modle.entity.BaseEntity;
import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.common.utils.MyBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: liangjinyin
 * @Date: 2018-08-30
 * @Description: service 的基类
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
public abstract class CrudService<D extends BaseDao, E extends BaseEntity> extends BaseService<E> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 根据id获取实体类
     */
    public Object findEntityById(Integer id) {
        try {
            entity = (E) dao.findById(id).get();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }


    /**
     * 添加实体或是修改实体
     */
    public Object saveOrUpdateEntity(E e1) {
        try {
            if (e1.getId() == null) {
                //添加
                return dao.save(e1);
            } else {
                //修改
                entity = (E) dao.findById(e1.getId()).get();
                if (entity == null) {
                    return ResultCode.ENTITY_NOT_EXIST;
                } else {
                    MyBeanUtils.copyProperties(e1, entity);
                    return dao.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    /**
     * 根据id删除实体
     */
    public Object deleteEntityById(Integer id) {
        try {
            if (dao.existsById(id)) {
                dao.deleteById(id);
                return ResultCode.OPERATION_SUCCESSED;
            } else {
                return ResultCode.ENTITY_NOT_EXIST;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    /**
     * 根据多个id删除实体
     */
    public Object deleteEntityIds(List<Integer> ids) {
        try {
            dao.deleteInBatch(ids);
            return ResultCode.OPERATION_SUCCESSED;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    /**
     * 查询所有不分页
     */
    public Object findAll() {
        try {
            return list = dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    /**
     * 查询所有分页
     */
    public Object findAllPage(Pageable pageable) {
        try {
            page = dao.findAll(pageable);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }
}
