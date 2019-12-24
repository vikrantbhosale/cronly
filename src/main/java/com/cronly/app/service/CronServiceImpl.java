package com.cronly.app.service;

import com.cronly.app.model.Cron;
import com.cronly.app.repository.CronRepository;
import com.cronly.app.util.CronWebHookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("cronService")
@Transactional
public class CronServiceImpl implements CronService {
    @Autowired
    private CronRepository cronRepository;

    @Autowired
    CronWebHookUtil cronWebHookUtil;


    public Cron findById(int id) {
        return cronRepository.findById(id).get();
    }

    @Override
    public void saveCron(Cron cron) {
        cronRepository.save(cron);
    }

    @Override
    public void deleteCron(Cron cron) {
        cronRepository.delete(cron);

    }

    @Override
    public void updateCron(Cron cron) {
//        dao.updateCron(cron);

    }

    @Override
    public Cron findByCronId(String cronID) {
        // TODO Auto-generated method stub
        return cronRepository.findByCronId(cronID);
    }

    @Override
    public Integer countAllCronsForId(String idPrefix) {
        return null;
//        return dao.countAllCronsForId(idPrefix);
    }

//    @Override
//    public List<Cron> findAllFailingCrons(Date date) throws JSONException {
//        List<Cron> failingCrons = dao.findAllFailingCrons(date);
//        System.out.println("failing crons size" + failingCrons.size());
//        JSONObject webHook = new JSONObject();
//        for (Cron cron : failingCrons) {
//            cron.setNotificationSent(true);
//            cron.setSuccess(false);
//            webHook.put("job_name", cron.getJobName());
//            webHook.put("status", false);
//            webHook.put("expected_run_time", cron.getStartTime());
//            webHook.put("job_id", cron.getJobId());
//            cronWebHookUtil.post(webHook, cron.getWebhookUrl());
//            JSONObject slk = cronWebHookUtil.createSlackNotification(cron.getJobName() + " did not run",
//                    cron.getJobName(), new Date());
//            cronWebHookUtil.post(slk, "https://hooks.slack.com/services/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");
//
//        }
//        return failingCrons;
//    }

    @Override
    public List<Cron> findAllCrons() {
        return null;
    }

}
