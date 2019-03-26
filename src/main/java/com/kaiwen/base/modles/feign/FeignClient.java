package com.kaiwen.base.modles.feign;

import feign.Headers;
import feign.RequestLine;

/**
 * @author: liangjinyin
 * @Date: 2018-10-04
 * @Description:  模块
 */
@Headers("Content-Type:application/json")
public interface FeignClient {
    @RequestLine("GET /customer/data")
    Object getDate();

    @RequestLine("GET /mlsdate/findList")
    Object findMls();
}
