package com.glt.cronjob.impl;

import com.google.inject.Inject;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.eventbus.ReplyFailure;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.CountDownLatch;


/**
 * Created by levin on 5/22/2015.
 */
public class JobImpl implements Job {

    private static final Logger log = LoggerFactory.getLogger(JobImpl.class);

    @Inject
    Vertx vertx;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        log.debug("stasrting execute job " + jobName);

//        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String address = (String) context.getMergedJobDataMap().get("address");
//        Boolean retryWhenFailure = (Boolean) context.getMergedJobDataMap().get("retryWhenFailure");
        String data = (String) context.getMergedJobDataMap().get("data");

        if(vertx == null || address == null){
            throw new JobExecutionException("vertx or address could not be null");
        }

        CountDownLatch latch = new CountDownLatch(1);

        Future fut = Future.future();

        JsonObject req = new JsonObject();
        req.put("job", jobName).put("data", new JsonObject(data));

        vertx.eventBus().send(address, req, ar -> {
            if (ar.succeeded()) {
                fut.complete();
            } else {
                ReplyException rex = (ReplyException) ar.cause();
                if (rex.failureType() == ReplyFailure.NO_HANDLERS ||
                        rex.failureType() == ReplyFailure.TIMEOUT) {

//                    if(retryWhenFailure) {
                    JobExecutionException e2 = new JobExecutionException();
                    e2.refireImmediately();
                    fut.fail(e2);
//                    }
//                    else{
//                        fut.complete();
//                    }

                } else {
                    //ignore others failure case
                    fut.complete();
                }
            }
            latch.countDown();
        });

        try{
            latch.await();
        }
        catch (Exception ex){
            //noop
        }

        log.debug("finished execute job " + jobName);

        if(fut.failed()){
            JobExecutionException e2 = (JobExecutionException)fut.cause();
            if(e2 != null)
                throw e2;
        }
    }
}
