package com.glt.cronjob.impl;

import com.google.inject.Injector;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 * Created by levin on 5/26/2015.
 */
public class GuiceJobFactory implements JobFactory {

    private final Injector injector;

    public GuiceJobFactory(Injector injector){
        this.injector = injector;
    }

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = triggerFiredBundle.getJobDetail();
        Class jobClass = jobDetail.getJobClass();

        try {
            // Get a new instance of that class from Guice so we can do dependency injection
            return (Job) injector.getInstance(jobClass);
        } catch (Exception e) {
            // Something went wrong.  Print out the stack trace here so SLF4J doesn't hide it.
            e.printStackTrace();
            // Rethrow the exception as an UnsupportedOperationException
            throw new UnsupportedOperationException(e);
        }

    }
}
