package com.kaiwen.base.modles.malasong.service;

import com.kaiwen.base.modles.malasong.entity.MlsDate;
import com.kaiwen.base.modles.malasong.repository.MlsDateRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

import org.springframework.data.domain.*;
import org.springframework.lang.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liangjinyin
 * @Date: 2018-12-27
 * @Description:
 */
@Service
@Slf4j
public class MlsDateService {

    @Autowired
    private MlsDateRepository mlsdateRepository;
    @Transactional(rollbackFor = Exception.class)
    public List<MlsDate> findPage() {
        try {
            List<MlsDate> dateByTimeAndStatus = mlsdateRepository.findDateByTimeAndStatus();
            mlsdateRepository.updateData();
            return dateByTimeAndStatus;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }
}