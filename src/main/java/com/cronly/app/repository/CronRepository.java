package com.cronly.app.repository;

import com.cronly.app.model.Cron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CronRepository extends JpaRepository<Cron, Integer> {

    @Query(value = "select * from `CRON_MONITOR` where `JOB_ID` = :cronID", nativeQuery = true)
    Cron findByCronId(@Param("cronID") String cronID);

}
