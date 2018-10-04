package com.kaiwen.base.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author: liangjinyin
 * @Date: 2018-08-31
 * @Description:
 */
@Data
public class CommonHttpResponse {
    private String entityStr;
    private int retCode;
    private String retMsg;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
