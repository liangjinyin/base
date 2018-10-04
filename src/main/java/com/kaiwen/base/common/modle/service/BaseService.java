package com.kaiwen.base.common.modle.service;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangjinyin
 * @date 20180813
 * @description 权限控制service层
 */
public abstract class BaseService<T> {

    protected String sql = null;
    protected Map<String, Object> data = new HashMap();
    protected List<Map<String, Object>> temp = null;
    protected List<T> list = null;
    protected T entity = null;
    protected Page<T> page = null;

}
