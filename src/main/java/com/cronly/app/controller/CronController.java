package com.cronly.app.controller;

import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.Hash;
import com.authy.api.Token;
import com.authy.api.Tokens;
import com.authy.api.Users;
import com.cronly.app.dto.CronRequestDTO;
import com.cronly.app.jobs.CronJob;
import com.cronly.app.model.Cron;
import com.cronly.app.repository.CronRepository;
import com.cronly.app.service.CronService;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;
import com.twilio.twiml.voice.Sip;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CronController {
    @Autowired
    CronRepository cronRepository;

    @Autowired
    CronService cronService;

    @Autowired
    CronJob cronJob;

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
    @GetMapping(value = "/{endPoint}/run")
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
    @GetMapping(value = "/{endPoint}/complete")
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

    @GetMapping(value = "/generateurl")
    public ResponseEntity<HashMap<Object, Object>> generateJsonTestUrl() throws JSONException {
        HashMap<Object, Object> resultMap = new HashMap<Object, Object>();
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        String completeUrl = "http://app.cronly.io/" + generatedString;
        cronJob.cronMonitor();
        resultMap.put("url", completeUrl);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }


    @PostMapping(value = "/{endPoint}")
    public ResponseEntity<String> jsonTest(@RequestBody String jsonString) {
        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

    @GetMapping(path = "/callconfig", produces = MediaType.APPLICATION_XML_VALUE)
    public String customerInformation(@RequestParam("Called") String Called) {

        String val = Called;
        //Called.replace("sip:","");
        return "<Response><Dial><Sip>" + val + "</Sip></Dial></Response>";

//        return cust;
    }

    @GetMapping(path = "/initialcondition", produces = MediaType.APPLICATION_XML_VALUE)
    public String initialCondition(@RequestParam("Digits") String digits) {
        VoiceResponse response = null;
//        String extractedNumber = digits.replace('*',null).substring(1);
        if (digits.charAt(0) == '*' && digits.charAt(1) == '*' ) {

            Number number = new Number.Builder("+" + digits.substring(2)).build();
            Dial dial = new Dial.Builder().callerId("+15855750088").number(number)
                    .build();
            response = new VoiceResponse.Builder().dial(dial).build();

        } else if (digits.charAt(0) == '*' && digits.charAt(1) != '*') {
            Sip sip = new Sip.Builder("sip:"+digits.substring(1)+"@iomeister.sip.us1.twilio.com").build();
            Dial dial = new Dial.Builder().sip(sip).build();
            response = new VoiceResponse.Builder().dial(dial).build();

        }
        return response.toXml();
    }

    @GetMapping(path = "/authy", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> verifyAuthyToken(@RequestParam("token") String token,
                                                @RequestParam("authyId") int authyId)  {
        HashMap<String,Object> responseMap = new HashMap();
        String API_KEY = "74ocjSUfhwxdZSPPlKLcmSn16Con1ZkI";
                //System.getenv("AUTHY_KEY");
        System.out.println("&*&*&*&*" + " " + API_KEY);
        AuthyApiClient client = new AuthyApiClient(API_KEY);

        Tokens tokens = client.getTokens();
        Token response = null;
        try {
            response = tokens.verify(authyId, token);
        } catch (AuthyException e) {
            e.printStackTrace();
            responseMap.put("success",false);
            return responseMap;
        }

        if (response.isOk()) {
            //System.out.println(response.toMap());
        } else {
            //System.out.println(response.getError());
        }
        responseMap.put("success",response.isOk());
        responseMap.put("isOk",response.isOk());
       // responseMap.put("message",response.getError().getMessage());
        responseMap.put("json",response.toJSON());
        return responseMap;
    }


}
