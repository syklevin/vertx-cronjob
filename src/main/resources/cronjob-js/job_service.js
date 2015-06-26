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

/** @module cronjob-js/job_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JJobService = com.glt.cronjob.JobService;

/**
 Created by levin on 5/22/2015.

 @class
*/
var JobService = function(j_val) {

  var j_jobService = j_val;
  var that = this;

  /**

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_jobService["close()"]();
    } else utils.invalidArgs();
  };

  /**

   @public
   @param jobDescriptor {Object} 
   @param resultHandler {function} 
   @return {JobService}
   */
  this.schedule = function(jobDescriptor, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && typeof __args[1] === 'function') {
      j_jobService["schedule(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(jobDescriptor), function(ar) {
      if (ar.succeeded()) {
        resultHandler(ar.result(), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**

   @public
   @param jobDescriptor {Object} 
   @param resultHandler {function} 
   @return {JobService}
   */
  this.unschedule = function(jobDescriptor, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && typeof __args[1] === 'function') {
      j_jobService["unschedule(io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](utils.convParamJsonObject(jobDescriptor), function(ar) {
      if (ar.succeeded()) {
        resultHandler(ar.result(), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_jobService;
};

/**

 @memberof module:cronjob-js/job_service
 @param vertx {Vertx} 
 @return {JobService}
 */
JobService.create = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(JJobService["create(io.vertx.core.Vertx)"](vertx._jdel), JobService);
  } else utils.invalidArgs();
};

/**

 @memberof module:cronjob-js/job_service
 @param vertx {Vertx} 
 @param address {string} 
 @return {JobService}
 */
JobService.createProxy = function(vertx, address) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(JJobService["createProxy(io.vertx.core.Vertx,java.lang.String)"](vertx._jdel, address), JobService);
  } else utils.invalidArgs();
};

// We export the Constructor function
module.exports = JobService;