package com.cronly.app.repository;

import com.cronly.app.model.Cron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronRepository extends JpaRepository<Cron, Integer> {

}
