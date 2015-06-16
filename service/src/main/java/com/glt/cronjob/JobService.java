package com.glt.cronjob;

import com.glt.cronjob.impl.JobServiceImpl;
import io.vertx.codegen.annotations.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

/**
 * Created by levin on 5/22/2015.
 */
@ProxyGen
@VertxGen
public interface JobService {

    public static final String JOB_SERVICE_ENDPOINT = "com.git.cronjob.acceptor";

    static JobService create(Vertx vertx){
        return new JobServiceImpl(vertx);
    }

    static JobService createProxy(Vertx vertx, String address){
        return ProxyHelper.createProxy(JobService.class, vertx, address);
    }

    @ProxyIgnore
    void close();

    @Fluent
    JobService schedule(JsonObject jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler);

    @Fluent
    JobService unschedule(JsonObject jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler);
}
