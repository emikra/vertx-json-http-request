package com.emikra.vertx.request;

import com.emikra.vertx.request.impl.DefaultRequestFactory;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Constructor;

@VertxGen
public interface RequestFactory {

    static RequestFactory create(Vertx vertx) {
        return new DefaultRequestFactory(vertx);
    }

    @GenIgnore
    static <T extends RequestFactory> T create(Vertx vertx, Class<T> factoryClass) {
        T instance;
        try {
            Constructor<T> constructor = factoryClass.getConstructor(Vertx.class);
            instance = constructor.newInstance(vertx);
        } catch (Exception e) {
             throw new RuntimeException(e);
        }

        return instance;
    }

    Request get(String url, Handler<Response> handler);

    default Request get(String url) {
        return get(url, null);
    }

    Request del(String url, JsonObject data, Handler<Response> handler);

    default Request del(String url, JsonObject data) {
        return del(url, data, null);
    }

    default Request del(String url) {
        return del(url, null, null);
    }

    default Request delete(String url, JsonObject data, Handler<Response> handler) {
        return del(url, data, handler);
    }

    default Request delete(String url, JsonObject data) {
        return delete(url, data, null);
    }

    default Request delete(String url) {
        return delete(url, null, null);
    }

    Request head(String url, JsonObject data, Handler<Response> handler);

    default Request head(String url, JsonObject data) {
        return head(url, data, null);
    }

    default Request head(String url) {
        return head(url, null, null);
    }

    Request patch(String url, JsonObject data, Handler<Response> handler);

    default Request patch(String url, JsonObject data) {
        return patch(url, data, null);
    }

    default Request patch(String url) {
        return patch(url, null, null);
    }

    Request post(String url, JsonObject data, Handler<Response> handler);

    default Request post(String url, JsonObject data) {
        return post(url, data, null);
    }

    default Request post(String url) {
        return post(url, null, null);
    }

    Request put(String url, JsonObject data, Handler<Response> handler);

    default Request put(String url, JsonObject data) {
        return put(url, data, null);
    }

    default Request put(String url) {
        return put(url, null, null);
    }

    RequestOptions defaultOptions();
}
