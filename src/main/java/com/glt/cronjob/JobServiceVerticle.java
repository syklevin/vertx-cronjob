package com.glt.cronjob;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by levin on 5/22/2015.
 */
public class JobServiceVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(JobServiceVerticle.class);

    protected JobService jobService;

    @Override
    public void start() throws Exception {
        final String address = config().getString("address", JobService.JOB_SERVICE_ENDPOINT);

        jobService = JobService.create(vertx);

        ProxyHelper.registerService(JobService.class, vertx, jobService, address);
    }

    @Override
    public void stop() throws Exception {
        if(jobService != null){
            jobService.close();
        }
    }
}
