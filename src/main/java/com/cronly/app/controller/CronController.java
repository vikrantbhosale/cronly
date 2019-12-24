package com.cronly.app.controller;

import com.cronly.app.model.Cron;
import com.cronly.app.repository.CronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class CronController {
@Autowired
    CronRepository cronRepository;

@Transactional
    @GetMapping(value = "/cron/")
    public ResponseEntity<List<Cron>> test() {
        List<Cron> cronList = cronRepository.findAll();
        return new ResponseEntity<>(cronList, HttpStatus.OK);
    }

}
