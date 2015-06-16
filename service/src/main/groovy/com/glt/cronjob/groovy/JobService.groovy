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
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * Created by levin on 5/22/2015.
*/
@CompileStatic
public class JobService {
  final def com.glt.cronjob.JobService delegate;
  public JobService(com.glt.cronjob.JobService delegate) {
    this.delegate = delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static JobService create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(com.glt.cronjob.JobService.create((io.vertx.core.Vertx)vertx.getDelegate()), com.glt.cronjob.JobService.class, com.glt.cronjob.groovy.JobService.class);
    return ret;
  }
  public static JobService createProxy(Vertx vertx, String address) {
    def ret= InternalHelper.safeCreate(com.glt.cronjob.JobService.createProxy((io.vertx.core.Vertx)vertx.getDelegate(), address), com.glt.cronjob.JobService.class, com.glt.cronjob.groovy.JobService.class);
    return ret;
  }
  public void close() {
    this.delegate.close();
  }
  public JobService schedule(Map<String, Object> jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler) {
    this.delegate.schedule(jobDescriptor != null ? new io.vertx.core.json.JsonObject(jobDescriptor) : null, resultHandler);
    return this;
  }
  public JobService unschedule(Map<String, Object> jobDescriptor, Handler<AsyncResult<Boolean>> resultHandler) {
    this.delegate.unschedule(jobDescriptor != null ? new io.vertx.core.json.JsonObject(jobDescriptor) : null, resultHandler);
    return this;
  }
}
