package com.cronly.app.service;

import com.cronly.app.model.Cron;
import com.oracle.javafx.jmx.json.JSONException;

import java.util.Date;
import java.util.List;

public interface CronService {
    Cron findById(int id);

    Cron findByCronId(String cronID);

    void saveCron(Cron cron);

    void updateCron(Cron cron);

    void deleteCron(Cron cron);

    List<Cron> findAllCrons();

//    List<Cron> findAllFailingCrons(Date date) throws JSONException;

    Integer countAllCronsForId(String idPrefix);

}
