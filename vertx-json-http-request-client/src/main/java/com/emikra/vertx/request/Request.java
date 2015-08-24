package com.emikra.vertx.request;

import com.emikra.vertx.request.plugins.RequestPlugin;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

@VertxGen
public interface Request {

    void abort();

    void end();

    @GenIgnore
    RequestOptions options();

    @Fluent
    default Request auth(String username, String password) {
        options().auth(username, password);
        return this;
    }

    @Fluent
    default Request accept(String accept) {
        options().accept(accept);
        return this;
    }

    @Fluent
    default Request attach(String name, String path, String filename) {
        options().attach(name, path, filename);
        return this;
    }

    @Fluent
    default Request field(String name, String value) {
        options().field(name, value);
        return this;
    }

    @Fluent
    default Request handler(Handler<Response> handler) {
        options().handler(handler);
        return this;
    }

    @Fluent
    default Request errorHandler(Handler<Response> handler) {
        options().errorHandler(handler);
        return this;
    }

    @Fluent
    default Request handler(int statusCode, Handler<Response> handler) {
        options().statusCodeHandler(statusCode, handler);
        return this;
    }

    @Fluent
    default Request param(String key, Object value) {
        options().param(key, value);
        return this;
    }

    @Fluent
    default Request param(JsonObject params) {
        options().param(params);
        return this;
    }

    @Fluent
    default Request redirects(int max) {
        options().redirects(max);
        return this;
    }

    @Fluent
    default Request send(String body) {
        options().send(body);
        return this;
    }

    @Fluent
    default Request send(JsonObject body) {
        options().send(body);
        return this;
    }

    @Fluent
    default Request set(String key, String value) {
        options().set(key, value);
        return this;
    }

    @Fluent
    default Request timeout(long ms) {
        options().timeout(ms);
        return this;
    }

    @Fluent
    default Request type(String type) {
        options().type(type);
        return this;
    }

    @Fluent
    default Request use(RequestPlugin plugin) {
        options().use(plugin);
        return this;
    }

    @Fluent
    default Request write(Buffer data) {
        options().write(data);
        return this;
    }

    @Fluent
    default Request host(String host) {
        options().host(host);
        return this;
    }

    @Fluent
    default Request port(int port) {
        options().port(port);
        return this;
    }
}
