package com.kaiwen.base.modles.ship.service;

import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.modles.ship.entity.Ship;
import com.kaiwen.base.modles.ship.repository.ShipRepository;
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
 * @Date: 2019-07-22
 * @Description:船舶 Service
 */
@Service
@Slf4j
public class ShipService {
    @Autowired
    private ShipRepository shipRepository;

     /**
     * 根据id获取船舶
     * @param id 船舶id
     * @return 船舶实体类
     */
    public Object findShipById(Integer id) {
        try {
            Ship ship = shipRepository.findById(id).get();
            return ship;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

     /**
     * 新增或者修改船舶
     * @param ship 船舶
     * @return 船舶实体类
     */
    @Transactional(rollbackFor = Exception.class)
    public Object saveShip(Ship ship) {
        try {
            return shipRepository.save(ship);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

     /**
     * 根据id删除船舶
     * @param id 船舶id
     * @return 船舶实体类
     */
    @Transactional(rollbackFor = Exception.class)
    public Object deleteShipById(Integer id) {
        try {
            if (shipRepository.existsById(id)) {
                shipRepository.deleteById(id);
            } else {
                return ResultCode.ENTITY_NOT_EXIST;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }
}