package com.kaiwen.base.modles.logger.controller;

import com.kaiwen.base.common.modle.controller.BaseController;
import com.kaiwen.base.common.modle.entity.LogQuery;
import com.kaiwen.base.modles.logger.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liangjinyin
 * @Date: 2018-09-25
 * @Description:  注解annotation
 */
@RestController
@RequestMapping("/logger")
public class LoggerController extends BaseController{

    @Autowired
    private LoggerService loggerService;

    @GetMapping("/save")
    public String saveLogger(){
        data = loggerService.analysisLogger();
        return result();
    }

    @GetMapping("/pageList")
    public String findLogList(Pageable pageable){
        data = loggerService.findAllPage(pageable);
        return result();
    }

    /**
     * 接口访问量排名
     * @return
     */
    @GetMapping("/access")
    public String findLogRanking(LogQuery logQuery){
        data = loggerService.findLogRanking(logQuery);
        return result();
    }

    /**
     * 系统访问量排名
     * @param logQuery
     * @return
     */
    @GetMapping("/dayAccess")
    public String findLogRankingByDay(LogQuery logQuery){
        data = loggerService.findLogRankingByDay(logQuery);
        return result();
    }

    /**
     * 接口访问量平均耗时排名
     * @param logQuery
     * @return
     */
    @GetMapping("/timeAccess")
    public String findLogRankingByTime(LogQuery logQuery){
        data = loggerService.findLogRankingByTime(logQuery);
        return result();
    }

    //最大访问量，最大耗时


}
