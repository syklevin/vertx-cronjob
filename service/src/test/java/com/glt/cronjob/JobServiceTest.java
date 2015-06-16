package com.glt.cronjob;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import io.vertx.test.core.VertxTestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by levin on 5/22/2015.
 */
public class JobServiceTest extends VertxTestBase {

    private static final Logger log = LoggerFactory.getLogger(JobServiceTest.class);

    private JobRegistrationCenter jobCenter;

    @Test
    public void testScheduleJob() throws Exception {

        CountDownLatch latch = new CountDownLatch(4);

        JsonObject data = new JsonObject().put("test1", "abc").put("test2", "456");
        jobCenter.schedule("testJob1", "0/5 * * * * ?", data, result -> {

            System.out.println("testJob1 job triggered: " + result.toString());
            latch.countDown();

            if(latch.getCount() == 4){
                jobCenter.unschedule("testJob1");
            }
        });

        latch.await(30, TimeUnit.SECONDS);

    }



    @Before
    public void startJobService() {
        CountDownLatch latch = new CountDownLatch(1);
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        vertx.deployVerticle("com.glt.cronjob.JobServiceVerticle", deploymentOptions ,r -> {
          if(r.succeeded()) {
              jobCenter = JobRegistrationCenter.create(vertx, JobService.JOB_SERVICE_ENDPOINT);
              log.info(r.result());
          } else {
              log.info("exception", r.cause());
          }
          latch.countDown();
        });
        try {
          latch.await();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }

    @After
    public void stopJobService() {
    }


}
