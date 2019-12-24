package com.cronly.app.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CRON_MONITOR")
public class Cron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "JOB_ID")
    private String jobId;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "GRACE_PERIOD")
    private Date gracePeriod;

    @Column(name = "CRON_EXPRESSION")
    private String cronExpression;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "RUN_TIME")
    private Date runTime;

    @Column(name = "COMPLETION_TIME")
    private Date completionTime;

    @Column(name = "NOTIFICATION_SENT")
    private Boolean notificationSent;

    @Column(name = "NOTIFICATION_DETAILS")
    private String notificationDetails;

    @Column(name = "WEBHOOK_URL")
    private String webhookUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Date gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(Boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    public String getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(String notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
