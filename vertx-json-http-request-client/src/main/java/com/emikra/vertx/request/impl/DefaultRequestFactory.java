package com.emikra.vertx.request.impl;

import com.emikra.vertx.request.Request;
import com.emikra.vertx.request.RequestFactory;
import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.Response;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DefaultRequestFactory implements RequestFactory {

    private static final Logger log = LoggerFactory.getLogger(DefaultRequestFactory.class);

    protected Vertx vertx;
    protected RequestOptions defaultOptions;

    public DefaultRequestFactory(Vertx vertx) {
        this(vertx, new DefaultRequestOptions(vertx));
    }

    public DefaultRequestFactory(Vertx vertx, DefaultRequestOptions defaultOptions) {
        this.vertx = vertx;
        this.defaultOptions = defaultOptions;
    }

    private Request request(HttpMethod method) {
        Request request = new DefaultRequest(defaultOptions);
        request.options().method(method);
        return request;
    }

    @Override
    public Request get(String url, Handler<Response> handler) {
        Request request = request(HttpMethod.GET);
        request.options().url(url);
        request.options().handler(handler);
        return request;
    }

    @Override
    public Request del(String url, JsonObject data, Handler<Response> handler) {
        Request request = request(HttpMethod.DELETE);
        request.options().url(url);
        request.options().body(data);
        request.options().handler(handler);
        return request;
    }

    @Override
    public Request head(String url, JsonObject data, Handler<Response> handler) {
        Request request = request(HttpMethod.HEAD);
        request.options().url(url);
        request.options().body(data);
        request.options().handler(handler);
        return request;
    }

    @Override
    public Request patch(String url, JsonObject data, Handler<Response> handler) {
        Request request = request(HttpMethod.PATCH);
        request.options().url(url);
        request.options().handler(handler);
        return request;
    }

    @Override
    public Request post(String url, JsonObject data, Handler<Response> handler) {
        Request request = request(HttpMethod.POST);
        request.options().url(url);
        request.options().body(data);
        request.options().handler(handler);
        return request;
    }

    @Override
    public Request put(String url, JsonObject data, Handler<Response> handler) {
        Request request = request(HttpMethod.PUT);
        request.options().url(url);
        request.options().body(data);
        request.options().handler(handler);
        return request;
    }

    public void replaceDefaultOptions(RequestOptions defaultOptions) {
        this.defaultOptions = defaultOptions;
    }

    public void mergeDefaultOptions(RequestOptions options) {
        this.defaultOptions.mergeOptions(options);
    }

    public RequestOptions defaultOptions() {
        return this.defaultOptions;
    }
}
