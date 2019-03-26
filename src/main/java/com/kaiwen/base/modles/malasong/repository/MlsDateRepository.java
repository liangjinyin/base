package com.kaiwen.base.modles.malasong.repository;

import com.kaiwen.base.modles.malasong.entity.MlsDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: liangjinyin
 * @Date: 2018-12-27
 * @Description:
 */
public interface MlsDateRepository extends JpaRepository<MlsDate, Integer> {

//    @Query(value = "SELECT * from hy_marathon a " +
//            "where a.ewdTime = DATE_FORMAT(now()-interval(TIME_TO_SEC(now()) mod 900 ) second,'%Y%m%d%H%i%s') and a.state = null ",nativeQuery = true)
    @Query(value = "SELECT * from hy_marathon a where a.ewd_time = '20181227150000' and a.state is null ",nativeQuery = true)
    List<MlsDate> findDateByTimeAndStatus();

    @Modifying
    @Query(value = "update hy_marathon set state=1 where state is null",nativeQuery = true)
    void updateData();
}