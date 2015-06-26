package com.glt.cronjob;

import com.glt.cronjob.impl.JobRegistrationCenterImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by levin on 5/22/2015.
 */
@VertxGen
public interface JobRegistrationCenter {

    static JobRegistrationCenter create(Vertx vertx, String address){
        return new JobRegistrationCenterImpl(vertx, address);
    }

    void close();

    @Fluent
    JobRegistrationCenter schedule(String name, String cron, JsonObject data, Handler<JsonObject> job);

    @Fluent
    JobRegistrationCenter unschedule(String name);
}
