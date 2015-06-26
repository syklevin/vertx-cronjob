package com.glt.cronjob.impl;

import com.glt.cronjob.JobService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by levin on 5/22/2015.
 */
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    private AtomicInteger counter = new AtomicInteger();

    private Scheduler scheduler;

    public JobServiceImpl(Vertx vertx) {
        try{
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
            this.scheduler.setJobFactory(new VertxJobFactory(vertx));
            this.scheduler.start();
        }
        catch (Exception ex){
            throw new RuntimeException("failed to create job scheduler", ex);
        }
    }

    @Override
    public void close(){
        try {
            if(this.scheduler != null) {
                this.scheduler.shutdown();
            }
        }
        catch (Exception ex){
            log.error("closing for job scheduler failure", ex);
        }
    }

    @Override
    public JobService schedule(JsonObject jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler){
        try {

            log.debug("do schedule job");

            String triggerAddress = jobDescriptor.getString("trigger");
            String cronExpression = jobDescriptor.getString("cron");
            if(triggerAddress == null || cronExpression == null){
                resultHandler.handle(Future.failedFuture(new IllegalArgumentException("triggerAddress or cronExpression could not be null")));
                return this;
            }

            String jobName = jobDescriptor.getString("job");
            if(jobName == null || jobName.length() <= 0)
                jobName = "vertx-job-" + this.counter.getAndIncrement();

            String jobGroup = jobDescriptor.getString("group");

            String traggerName = jobDescriptor.getString("triggerName");
            if(traggerName == null || traggerName.length() <= 0)
                traggerName = "vertx-trigger-" + this.counter.getAndIncrement();

            String traggerGroup = jobDescriptor.getString("triggerGroup");

            JobKey jobKey = new JobKey(jobName, jobGroup);
            TriggerKey triggerKey = new TriggerKey(traggerName, traggerGroup);

//            Boolean retryWhenFailure = jobDescriptor.getBoolean("retryWhenFailure", false);

            JobDataMap jobDataMap = new JobDataMap();
//            jobDataMap.put("eventBus", vertx.eventBus());
            jobDataMap.put("address", triggerAddress);
//            jobDataMap.put("retryWhenFailure", retryWhenFailure);
            //must put as string for serializable
            jobDataMap.put("data", jobDescriptor.getJsonObject("data", new JsonObject()).toString());

            JobDetail jobDetail = newJob(VertxJob.class)
                    .withIdentity(jobKey)
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronSchedule(cronExpression))
                    .forJob(jobKey)
                    .build();

            this.scheduler.scheduleJob(jobDetail, trigger);
            resultHandler.handle(Future.succeededFuture(true));
        }
        catch (SchedulerException ex){
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }

    @Override
    public JobService unschedule(JsonObject jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler){
        try {
            String jobName = jobDescriptor.getString("job");
            String jobGroup = jobDescriptor.getString("group");
            boolean success = this.scheduler.deleteJob(new JobKey(jobName, jobGroup));
            resultHandler.handle(Future.succeededFuture(success));
        } catch (SchedulerException ex) {
            resultHandler.handle(Future.failedFuture(ex));
        }
        return this;
    }
}
