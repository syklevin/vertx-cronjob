/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.glt.cronjob.groovy;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.groovy.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.Handler
/**
 * Created by levin on 5/22/2015.
*/
@CompileStatic
public class JobRegistrationCenter {
  final def com.glt.cronjob.JobRegistrationCenter delegate;
  public JobRegistrationCenter(com.glt.cronjob.JobRegistrationCenter delegate) {
    this.delegate = delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static JobRegistrationCenter create(Vertx vertx, String address) {
    def ret= InternalHelper.safeCreate(com.glt.cronjob.JobRegistrationCenter.create((io.vertx.core.Vertx)vertx.getDelegate(), address), com.glt.cronjob.JobRegistrationCenter.class, com.glt.cronjob.groovy.JobRegistrationCenter.class);
    return ret;
  }
  public void close() {
    this.delegate.close();
  }
  public JobRegistrationCenter schedule(String name, String cron, Map<String, Object> data, Handler<Map<String, Object>> job) {
    this.delegate.schedule(name, cron, data != null ? new io.vertx.core.json.JsonObject(data) : null, new Handler<JsonObject>() {
      public void handle(JsonObject event) {
        job.handle((Map<String, Object>)InternalHelper.wrapObject(event));
      }
    });
    return this;
  }
  public JobRegistrationCenter unschedule(String name) {
    this.delegate.unschedule(name);
    return this;
  }
}
