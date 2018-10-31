package com.kaiwen.base.modles.logger.dao;

import com.kaiwen.base.common.modle.dao.BaseDao;
import com.kaiwen.base.common.modle.entity.LogQuery;
import com.kaiwen.base.modles.logger.entity.LoggerInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: liangjinyin
 * @Date: 2018-09-25
 * @Description:
 */

public interface LoggerDao extends BaseDao<LoggerInfo, Integer> {

    @Override
    @Modifying
    <S extends LoggerInfo> List<S> saveAll(Iterable<S> iterable);

    @Query(nativeQuery = true,
            value = "SELECT count(DISTINCT(id)) visit,url FROM sys_log" +
                    " WHERE DATE_FORMAT(log_time,'%Y%m%d') BETWEEN :#{#log.startDate} AND :#{#log.endDate} " +
                    " GROUP BY url ORDER BY  visit DESC")
    List<Map<String, Object>> findLogRanking(@Param("log") LogQuery logQuery);

    @Query(nativeQuery = true,
            value = "SELECT count(DISTINCT(id)) visit,DATE_FORMAT(log_time,'%Y%m%d') data FROM sys_log" +
                    " WHERE DATE_FORMAT(log_time,'%Y%m%d')  BETWEEN :#{#log.startDate} AND :#{#log.endDate} " +
                    " GROUP BY data ORDER BY  visit DESC")
    List<Map<String, Object>> findLogRankingByDay(@Param("log")LogQuery logQuery);


   @Query(nativeQuery = true,
            value = "SELECT a.id,a.username,a.url,a.`time`,a.log_time,a.params FROM sys_log a " +
                    " WHERE DATE_FORMAT(a.log_time,'%Y%m%d')  BETWEEN :#{#log.startDate} AND :#{#log.endDate} ")
    List<LoggerInfo>  findLogRankingByTime(@Param("log")LogQuery logQuery);


}
