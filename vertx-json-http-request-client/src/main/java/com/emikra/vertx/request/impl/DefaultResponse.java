package com.emikra.vertx.request.impl;

import com.emikra.vertx.request.Response;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DefaultResponse implements Response {

    private static final Logger log = LoggerFactory.getLogger(DefaultResponse.class);

    private int status;
    private MultiMap headers;
    private String body;

    public DefaultResponse(HttpClientResponse response, String body) {
        this.status = response.statusCode();
        this.headers = response.headers();
        this.body = body;
    }

    public String bodyString() {
        return this.body;
    }

    @Override
    public int status() {
        return this.status;
    }

    @Override
    public MultiMap headers() {
        return this.headers;
    }

    @Override
    public Boolean success() {
        return !error();
    }

    @Override
    public Boolean error() {
        return this.status >= 300;
    }

    @Override
    public JsonObject toJson() {

        JsonObject response = new JsonObject();

        response.put("status", this.status);

        try {
            response.put("body", this.bodyJsonObject());
        } catch (Exception e) {
            response.put("body", new JsonObject());
        }

        JsonObject jsonHeaders = new JsonObject();

        this.headers.forEach(entry -> jsonHeaders.put(entry.getKey(), entry.getValue()));

        response.put("headers", jsonHeaders);

        return response;
    }
}
