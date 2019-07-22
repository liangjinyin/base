package com.kaiwen.base.modles.ship.controller;

import com.kaiwen.base.common.modle.controller.BaseController;
import com.kaiwen.base.modles.ship.entity.Ship;
import com.kaiwen.base.modles.ship.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: liangjinyin
 * @Date: 2019-07-22
 * @Description:船舶 Controller
 */
@RestController
@RequestMapping(value = "/ship",produces = "application/json; charset=utf-8")
public class ShipController extends BaseController {
    @Autowired
    private ShipService shipService;

     /**
     * 根据id获取船舶
     * @param id 船舶id
     * @return 船舶实体类
     */
    @GetMapping("/findOne")
    public String findShipById(Integer id){
        data = shipService.findShipById(id);
        return result();
    }



     /**
     * 新增或者修改船舶
     * @param ship 船舶
     * @return 船舶实体类
     */
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateShip(@RequestBody Ship ship){
        data = shipService.saveShip(ship);
        return result();
    }

     /**
     * 根据id删除船舶
     * @param id 船舶id
     * @return 船舶实体类
     */
    @GetMapping("/delete")
    public String deleteShipById(Integer id){
        data = shipService.deleteShipById(id);
        return result();
    }

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/expireShip")
    public String expireShip(){
        redisTemplate.expire("aisShipHistory", 1, TimeUnit.SECONDS);
        return result();
    }
}