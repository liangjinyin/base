package com.kaiwen.base.modles.malasong.controller;

import com.kaiwen.base.modles.malasong.service.MlsDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liangjinyin
 * @Date: 2018-12-27
 * @Description:
 */
@RestController
@RequestMapping(value = "/mlsdate", produces = "application/json; charset=utf-8")
public class MlsDateController {
    @Autowired
    private MlsDateService mlsdateService;

    @GetMapping("/findList")
    public Object findAllMlsDate() {
        return mlsdateService.findPage();
    }

}