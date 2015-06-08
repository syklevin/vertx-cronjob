package com.glt.cronjob;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

/**
 * Created by levin on 5/22/2015.
 */
public class JobServiceVerticleTest extends VertxTestBase {

    private static final Logger log = LoggerFactory.getLogger(JobServiceVerticleTest.class);

    @Test
    public void testDeployService() {
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(true);
        vertx.deployVerticle("service:com.glt.vertx-cronjob", deploymentOptions, r -> {
            if (r.succeeded()) {
                log.info(r.result());
                testComplete();
            } else {
                log.info("exception", r.cause());
                fail(r.cause().toString());
            }
        });

        await();
    }
}
