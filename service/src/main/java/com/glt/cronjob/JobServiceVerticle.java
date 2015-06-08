package com.glt.cronjob;

import com.glt.cronjob.impl.GuiceVertxBinder;
import com.glt.cronjob.impl.JobServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by levin on 5/22/2015.
 */
public class JobServiceVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(JobServiceVerticle.class);

    protected JobService jobService;

    protected Injector setupGuice(Module... modules) {
        List<Module> allModules = new ArrayList<>();
        allModules.add(new GuiceVertxBinder(vertx));
        Module bootstrap = null;
        String bootstrapBinder = config().getString("guiceBinder");
        try {

            if (bootstrapBinder != null && bootstrapBinder.length() > 0) {
                Class<?> bootstrapClz = Class.forName(bootstrapBinder);

                Object obj = bootstrapClz.newInstance();

                if (obj instanceof Module) {
                    bootstrap = (Module) obj;
                } else {
                    log.error("Class " + bootstrapBinder
                            + " does not implement Module.");
                }
            } else {
                log.info("no bootstrapBinder given");
            }
        } catch (Exception ex) {
            log.error("Guice bootstrap binder class " + bootstrapBinder
                    + " was not found.  Are you missing injection bindings?");
        }

        for (Module m : modules) {
            allModules.add(m);
        }

        if (bootstrap != null) {
            log.info("added bootstrap binder");
            allModules.add(bootstrap);
        }

        Injector injector = Guice.createInjector(allModules);
        return injector;
    }


    @Override
    public void start() throws Exception {
        final String address = config().getString("address", JobService.JOB_SERVICE_ENDPOINT);
        Injector injector = setupGuice();
        jobService = injector.getInstance(JobServiceImpl.class);
        ProxyHelper.registerService(JobService.class, vertx, jobService, address);
    }

    @Override
    public void stop() throws Exception {
        if(jobService != null){
            jobService.close();
        }
    }
}
