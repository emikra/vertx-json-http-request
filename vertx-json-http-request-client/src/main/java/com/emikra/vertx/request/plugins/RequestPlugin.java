package com.emikra.vertx.request.plugins;

import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.Response;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

@VertxGen
public interface RequestPlugin extends Handler<RequestOptions> {

    default void handleResponse(Response response) {

    }
}
