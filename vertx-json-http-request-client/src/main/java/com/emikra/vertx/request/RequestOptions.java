package com.emikra.vertx.request;

import com.emikra.vertx.request.plugins.RequestPlugin;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

@VertxGen
public interface RequestOptions {

    @GenIgnore
    HttpClientRequest getVertxRequest();

    @GenIgnore
    Vertx vertx();

    @GenIgnore
    String auth();

    @GenIgnore
    HttpMethod method();

    @GenIgnore
    String url();

    @GenIgnore
    String urlWithQueryParams();

    @GenIgnore
    String body();

    @GenIgnore
    void body(String body);

    @GenIgnore
    void body(JsonObject body);

    @GenIgnore
    String host();

    @GenIgnore
    int port();

    @GenIgnore
    Handler<Response> handler();

    @GenIgnore
    Handler<Response> errorHandler();

    @GenIgnore
    Handler<Response> statusCodeHandler(int statusCode);

    @GenIgnore
    Map<String, Object> params();

    @GenIgnore
    RequestOptions copy();

    @GenIgnore
    RequestOptions mergeOptions(RequestOptions options);

    @Fluent
    RequestOptions handler(Handler<Response> handler);

    @Fluent
    RequestOptions errorHandler(Handler<Response> handler);

    @Fluent
    RequestOptions statusCodeHandler(int statusCode, Handler<Response> handler);

    @GenIgnore
    List<RequestPlugin> plugins();

    @GenIgnore
    MultiMap headers();

    @Fluent
    RequestOptions auth(String username, String password);

    @Fluent
    RequestOptions accept(String accept);

    @Fluent
    RequestOptions attach(String name, String path, String filename);

    @Fluent
    RequestOptions field(String name, String value);

    @Fluent
    RequestOptions param(String key, Object value);

    @Fluent
    RequestOptions param(JsonObject params);

    @Fluent
    RequestOptions redirects(int max);

    @Fluent
    RequestOptions send(String body);

    @Fluent
    RequestOptions send(JsonObject body);

    @Fluent
    RequestOptions set(String key, String value);

    @Fluent
    RequestOptions timeout(long ms);

    @Fluent
    RequestOptions type(String type);

    @Fluent
    RequestOptions use(RequestPlugin plugin);

    @Fluent
    RequestOptions write(Buffer data);

    @Fluent
    RequestOptions host(String host);

    @Fluent
    RequestOptions port(int port);

    @Fluent
    RequestOptions url(String url);

    @Fluent
    RequestOptions method(HttpMethod method);
}
