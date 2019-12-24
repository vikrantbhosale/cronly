package com.cronly.app.controller;

import com.cronly.app.dto.CronRequestDTO;
import com.cronly.app.model.Cron;
import com.cronly.app.repository.CronRepository;
import com.cronly.app.service.CronService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class CronController {
    @Autowired
    CronRepository cronRepository;

    @Autowired
    CronService cronService;

    @Transactional
    @GetMapping(value = "/cron/")
    public ResponseEntity<List<Cron>> test() {
        List<Cron> cronList = cronRepository.findAll();
        return new ResponseEntity<>(cronList, HttpStatus.OK);
    }

    @PostMapping(value = "/cron/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Object, Object>> createCron(@RequestBody CronRequestDTO cronRequestDTO) {
        HashMap<Object, Object> resultMap = new HashMap<Object, Object>();
        int length = 6;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        Cron cron = new Cron();
        cron.setJobId(generatedString);
        cron.setJobName(cronRequestDTO.getJobName());
        cron.setSuccess(false);
        cron.setNotificationSent(false);
        cron.setNotificationDetails(cronRequestDTO.getNotificationDetails());
        final String cronExpression = cronRequestDTO.getCronExpression();
        final CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
        final Date nextExecutionDate = generator.next(new Date());
        cron.setStartTime(nextExecutionDate);
        cron.setGracePeriod(nextExecutionDate);
        cron.setWebhookUrl(cronRequestDTO.getWebhookUrl());
        cronRepository.save(cron);

        resultMap.put("cron_id", cron.getJobId());
//        Sentry.clearContext();
        return new ResponseEntity<HashMap<Object, Object>>(resultMap, HttpStatus.CREATED);
    }

    // -------------------Update On Run Ping
    // --------------------------------------------------------
    @PostMapping(value = "/{endPoint}/run")
    public ResponseEntity<HashMap<Object, Object>> cronRunUpdate(@PathVariable String endPoint) {
        HashMap<Object, Object> resultMap = new HashMap<Object, Object>();
        Cron cron = cronService.findByCronId(endPoint);
        cron.setSuccess(true);
        cron.setRunTime(new Date());
        resultMap.put("success", true);
        resultMap.put("cron_id", cron.getJobId());
        cronService.updateCron(cron);

        return new ResponseEntity<HashMap<Object, Object>>(resultMap, HttpStatus.OK);
    }

    // -------------------Update On Complete Ping
    // --------------------------------------------------------
    @PostMapping(value = "/{endPoint}/complete")
    public ResponseEntity<HashMap<Object, Object>> cronCompleteUpdate(@PathVariable String endPoint) {
        HashMap<Object, Object> resultMap = new HashMap<Object, Object>();
        Cron cron = cronService.findByCronId(endPoint);
        cron.setSuccess(true);
        cron.setCompletionTime(new Date());
        resultMap.put("success", true);
        resultMap.put("cron_id", cron.getJobId());
        cronService.updateCron(cron);

        return new ResponseEntity<HashMap<Object, Object>>(resultMap, HttpStatus.OK);
    }

//	// -------------------Retrieve All Cron Categorys for a
//	// --------------------------------------------------------
//	@GetMapping(value = "/cron/creditcaregory")
//	public ResponseEntity<List<CronPurposeCategory>> listAllCronPurposeCategories() {
//		List<CronPurposeCategory> categories = cronPurposeCategoryService.findAllCronPurposeCategorys();
//		if (categories.isEmpty()) {
//			return new ResponseEntity<List<CronPurposeCategory>>(Collections.EMPTY_LIST, HttpStatus.OK);
//		}
////			List<StateResponseDTO> stateDtos = states.stream()
////					.map(state -> convertStateDtoToResponseDto(state)).collect(Collectors.toList());
//		return new ResponseEntity<List<CronPurposeCategory>>(categories, HttpStatus.OK);
//	}

    private String generateStanzaCronPrefix() {
        StringBuilder prefix = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        if (month <= 3) {
            prefix.append(String.valueOf(year - 1).substring(2) + String.valueOf(year).substring(2));
        } else {
            prefix.append(String.valueOf(year).substring(2) + String.valueOf(year + 1).substring(2));
        }
        prefix.append("/" + String.format("%02d", month) + "/" + "CN");
        return prefix.toString();
    }

    private String generateStanzaCronId(int prevCount) {
        int a = prevCount / 99999;
        int b = a % 26;
        int c = (a / 26) % 26;
        return Character.toString((char) (65 + c)) + Character.toUpperCase((char) (65 + b))
                + String.format("%05d", (1 + prevCount % 100000));
    }

    private String getCleanDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = df.format(new Date());
        return dateStr;
    }

}
