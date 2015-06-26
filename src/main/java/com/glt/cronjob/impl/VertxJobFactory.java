package com.glt.cronjob.impl;

import io.vertx.core.Vertx;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 * Created by levin on 6/16/2015.
 */
public class VertxJobFactory implements JobFactory {

    private final Vertx vertx;

    public VertxJobFactory(Vertx vertx){
        this.vertx = vertx;
    }

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        return new VertxJob(vertx);
    }
}
