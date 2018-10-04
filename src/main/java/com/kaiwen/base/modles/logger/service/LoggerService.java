package com.kaiwen.base.modles.logger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwen.base.common.modle.entity.LogQuery;
import com.kaiwen.base.common.modle.service.CrudService;
import com.kaiwen.base.common.enums.ResultCode;
import com.kaiwen.base.common.filter.LogFilter;
import com.kaiwen.base.common.utils.DateUtils;
import com.kaiwen.base.common.utils.ReadFileUtils;
import com.kaiwen.base.modles.logger.dao.LoggerDao;
import com.kaiwen.base.modles.logger.entity.LoggerInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author: liangjinyin
 * @Date: 2018-09-25
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LoggerService extends CrudService<LoggerDao, LoggerInfo> {

    public static final String LOG_PATH = "G:/home/myLog/logs/logger-visit-%s.log";

    public Object analysisLogger() {
        try {
            String date = DateUtils.getDate("YYYY-MM-dd");
            String path = String.format(LOG_PATH, date);
            //读取日志文件信息
            String readFile = ReadFileUtils.ReadFile(path);
            String logString = StringUtils.replaceAll(readFile, LogFilter.LOG_STARTS, "");
            log.info("logString:【{}】", logString);
            String[] urls = StringUtils.split(logString, "**");
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> list = Arrays.asList(urls);
            list = list.subList(0,list.size()-2);
            List<LoggerInfo> collect = list.stream().map(e -> {
                LoggerInfo loggerInfo = null;
                try {
                    loggerInfo = objectMapper.readValue(e, LoggerInfo.class);
                    loggerInfo.setUsername("zhangsan");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    log.error("将log转换为log对象时发生异常：{}",e1.getMessage());
                }
                return loggerInfo;
            }).collect(Collectors.toList());
            log.info("collect :【{}】", collect);
            //将信息存入数据库
            List<LoggerInfo> loggerInfos = super.dao.saveAll(collect);
            if (loggerInfos == null) {
                return ResultCode.OPERATION_FAILED;
            }
            return ResultCode.OPERATION_SUCCESSED;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}", getClass().getSimpleName(), e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    public Object findLogRanking(LogQuery logQuery) {
        try {
            temp = super.dao.findLogRanking(logQuery);
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}",getClass().getSimpleName(),e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    public Object findLogRankingByDay(LogQuery logQuery) {
        try {
            return super.dao.findLogRankingByDay(logQuery);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}",getClass().getSimpleName(),e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }

    public Object findLogRankingByTime(LogQuery logQuery) {
        try {
            list = super.dao.findLogRankingByTime(logQuery);
            Map<String, IntSummaryStatistics> collect = list.stream().collect(Collectors.groupingBy(LoggerInfo::getUrl, Collectors.summarizingInt(LoggerInfo::getTime)));
            return collect;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} 类出现了异常，异常信息为：{}",getClass().getSimpleName(),e.getMessage());
            return ResultCode.OPERATION_FAILED;
        }
    }
}
