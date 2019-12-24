package com.cronly.app.util;



import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
@Component
public class CronWebHookUtil {

    private static final Logger logger = LoggerFactory.getLogger(CronWebHookUtil.class);

    public void post(JSONObject jo, String webHookURI) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("Cronly.io Custom request to webhook  " + jo.toString());
        HttpEntity<String> entity = new HttpEntity<String>(jo.toString(), headers);
        restTemplate.exchange(webHookURI, HttpMethod.POST, entity, String.class);

    }

    public JSONObject createSlackNotification(String headerMessage, String jobName, Date date) throws JSONException {

        JSONObject slackCustomObj = new JSONObject();
        JSONArray slackBlocks = new JSONArray();

        slackCustomObj.put("channel",  "CRR2EG44R");
        slackCustomObj.put("blocks", slackBlocks);
        JSONObject blockObject = new JSONObject();
        JSONObject blockInnerObject = new JSONObject();
        blockInnerObject.put("type", "plain_text");
        blockInnerObject.put("emoji", true);
        blockInnerObject.put("text", headerMessage);
        blockObject.put("type", "section");
        blockObject.put("text", blockInnerObject);
        slackBlocks.put(blockObject);
        return slackCustomObj;
    }

}
