package com.glt.cronjob.impl;

import com.glt.cronjob.JobRegistrationCenter;
import com.glt.cronjob.JobService;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by levin on 5/22/2015.
 */
public class JobRegistrationCenterImpl implements JobRegistrationCenter {

    private static final String JOB_REGISTRATION_CENTER_PREFIX = "com.glt.cronjob.reg.center.";

    private final Vertx vertx;
    private final String instanceId;
    private final String address;
    private final JobService jobService;
    private final MessageConsumer<JsonObject> jobConsumer;

    private final Map<String, Handler<JsonObject>> jobs;

    public JobRegistrationCenterImpl(Vertx vertx, String address){
        this(vertx, ProxyHelper.createProxy(JobService.class, vertx, address));
    }

    public JobRegistrationCenterImpl(Vertx vertx, JobService jobService){
        this.vertx = vertx;
        this.jobService = jobService;
        this.instanceId = UUID.randomUUID().toString();
        this.jobs = new HashMap<>();
        this.address = JOB_REGISTRATION_CENTER_PREFIX + instanceId;
        this.jobConsumer = this.vertx.eventBus().consumer(address);
        this.jobConsumer.handler(this::onJob);
    }

    @Override
    public void close(){
        this.jobConsumer.unregister();
        this.jobs.clear();
    }

    @Override
    public JobRegistrationCenter schedule(String name, String cron, JsonObject data, Handler<JsonObject> job){
        JsonObject jobDescriptor = new JsonObject();
        jobDescriptor.put("job", name);
        jobDescriptor.put("group", instanceId);
        jobDescriptor.put("cron", cron);
        jobDescriptor.put("trigger", address);
        jobDescriptor.put("triggerName", "trigger-" + name);
        jobDescriptor.put("triggerGroup", "trigger-" + instanceId);
        jobDescriptor.put("data", data);
        jobService.schedule(jobDescriptor, ar -> {
            if(ar.succeeded()){
                jobs.put(name, job);
            }
            else{
                //show error
            }
        });
        return this;
    }

    @Override
    public JobRegistrationCenter unschedule(String name){
        JsonObject jobDescriptor = new JsonObject();
        jobDescriptor.put("job", name);
        jobDescriptor.put("group", instanceId);
        jobs.remove(name);
        return this;
    }

    private void onJob(Message<JsonObject> message) {
        //reply the message immediately
        message.reply(new JsonObject().put("success", true));

        JsonObject jobDescriptor = message.body();
        String jobName = jobDescriptor.getString("job");
        JsonObject data = jobDescriptor.getJsonObject("data");
        Handler<JsonObject> job = jobs.get(jobName);
        if(job != null){
            job.handle(data);
        }
    }

}
