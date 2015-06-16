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

/** @module cronjob-js/job_registration_center */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JJobRegistrationCenter = com.glt.cronjob.JobRegistrationCenter;

/**
 Created by levin on 5/22/2015.

 @class
*/
var JobRegistrationCenter = function(j_val) {

  var j_jobRegistrationCenter = j_val;
  var that = this;

  /**

   @public

   */
  this.close = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_jobRegistrationCenter["close()"]();
    } else utils.invalidArgs();
  };

  /**

   @public
   @param name {string} 
   @param cron {string} 
   @param data {Object} 
   @param job {function} 
   @return {JobRegistrationCenter}
   */
  this.schedule = function(name, cron, data, job) {
    var __args = arguments;
    if (__args.length === 4 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'object' && typeof __args[3] === 'function') {
      j_jobRegistrationCenter["schedule(java.lang.String,java.lang.String,io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](name, cron, utils.convParamJsonObject(data), function(jVal) {
      job(utils.convReturnJson(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /**

   @public
   @param name {string} 
   @return {JobRegistrationCenter}
   */
  this.unschedule = function(name) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'string') {
      j_jobRegistrationCenter["unschedule(java.lang.String)"](name);
      return that;
    } else utils.invalidArgs();
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_jobRegistrationCenter;
};

/**

 @memberof module:cronjob-js/job_registration_center
 @param vertx {Vertx} 
 @param address {string} 
 @return {JobRegistrationCenter}
 */
JobRegistrationCenter.create = function(vertx, address) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(JJobRegistrationCenter["create(io.vertx.core.Vertx,java.lang.String)"](vertx._jdel, address), JobRegistrationCenter);
  } else utils.invalidArgs();
};

// We export the Constructor function
module.exports = JobRegistrationCenter;