package com.glt.cronjob.impl;

import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;

/**
 * Created by levin on 5/26/2015.
 */
public class GuiceVertxBinder extends AbstractModule {

    private final Vertx vertx;

    public GuiceVertxBinder(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    protected void configure() {
        bind(Vertx.class).toInstance(vertx);
    }
}
