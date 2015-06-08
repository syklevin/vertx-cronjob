package com.glt.cronjob;

import io.vertx.codegen.annotations.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Created by levin on 5/22/2015.
 */
@ProxyGen
@VertxGen
public interface JobService {

    public static final String JOB_SERVICE_ENDPOINT = "com.git.cronjob.acceptor";

    @ProxyIgnore
    void close();

    @Fluent
    JobService schedule(JsonObject jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler);

    @Fluent
    JobService unschedule(JsonObject jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler);
}
