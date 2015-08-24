package com.emikra.vertx.request.impl;

import com.emikra.vertx.request.RequestException;
import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.Response;
import com.emikra.vertx.request.plugins.RequestPlugin;
import com.emikra.vertx.request.util.StringUtils;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.*;

public class DefaultRequestOptions implements RequestOptions {

    private static final Logger log = LoggerFactory.getLogger(DefaultRequestOptions.class);

    private Vertx vertx;

    private String authorization;
    private String body;
    private String host;
    private HttpMethod method;
    private int port;
    private String url;

    private Handler<Response> handler;
    private Handler<Response> errorHandler;
    private Map<Integer, Handler<Response>> statusCodeHandlers = new LinkedHashMap<>();

    private MultiMap headers = MultiMap.caseInsensitiveMultiMap();
    private Map<String, Object> params = new LinkedHashMap<>();
    private List<RequestPlugin> plugins = new LinkedList<>();

    public DefaultRequestOptions(Vertx vertx) {
        this.vertx = vertx;
    }

    public DefaultRequestOptions(RequestOptions options) {
        mergeOptions(options);
    }

    public RequestOptions mergeOptions(RequestOptions options) {

        if (options.vertx() != null) {
            this.vertx = options.vertx();
        } else {
            this.vertx = Vertx.vertx();
        }

        if (!StringUtils.isBlank(options.auth())) {
            this.authorization = options.auth();
        }

        if (!StringUtils.isBlank(options.body())) {
            this.body = options.body();
        }

        if (!StringUtils.isBlank(options.host())) {
            this.host = options.host();
        }

        if (options.method() != null) {
            this.method = options.method();
        }

        if (!(options.port() == 0)) {
            this.port = options.port();
        }

        if (!StringUtils.isBlank(options.url())) {
            this.url = options.url();
        }

        if (options.handler() != null) {
            this.handler = options.handler();
        }

        if (options.errorHandler() != null) {
            this.errorHandler = options.errorHandler();
        }

        if (options.headers() != null) {
            this.headers = MultiMap.caseInsensitiveMultiMap().addAll(options.headers());
        }

        if (options.params() != null) {
            this.params = new LinkedHashMap<>();
            this.params.putAll(options.params());
        }

        if (options.plugins() != null) {
            this.plugins = new LinkedList<>();
            this.plugins.addAll(options.plugins());
        }

        return this;
    }

    @Override
    public RequestOptions copy() {

        DefaultRequestOptions copy = new DefaultRequestOptions(this.vertx);

        copy.authorization = this.authorization;
        copy.body = this.body;
        copy.host = this.host;
        copy.method = this.method;
        copy.port = this.port;
        copy.url = this.url;

        copy.handler = handler;
        copy.errorHandler = errorHandler;
        copy.statusCodeHandlers = new LinkedHashMap<>();
        copy.statusCodeHandlers.putAll(this.statusCodeHandlers);
        copy.headers = MultiMap.caseInsensitiveMultiMap().addAll(this.headers());
        copy.params = new LinkedHashMap<>();
        copy.params.putAll(this.params());
        copy.plugins = new LinkedList<>();
        copy.plugins.addAll(this.plugins());

        return copy;
    }

    public DefaultRequestOptions(JsonObject json) {

    }

    public DefaultRequestOptions(DefaultRequestOptions options) {
        this.vertx = options.vertx;
        this.url = options.url;
        this.body = options.body;
        this.authorization = options.authorization;
    }

    /* RequestOptions Implementation */

    @Override
    public HttpClientRequest getVertxRequest() {
        HttpClientRequest request = this.vertx.createHttpClient()
                .request(this.method, this.port, this.host, this.urlWithQueryParams());


        request.handler(result -> result.bodyHandler(body -> {

            Response response = new DefaultResponse(result, body.toString());

            this.plugins.forEach(plugin -> plugin.handleResponse(response));

            if (this.statusCodeHandler(response.status()) != null) {
                this.statusCodeHandler(response.status()).handle(response);
                return;
            }

            if (this.errorHandler != null && response.error()) {
                this.errorHandler.handle(response);
                return;
            }

            this.handler().handle(response);
        }));

        request.exceptionHandler(error -> {
            throw new RequestException(error);
        });


        this.set("Content-Type", "application/json");

        if (!StringUtils.isBlank(this.auth())) {
            this.set("Authorization", this.auth());
        }

        this.headers.forEach(entry -> request.putHeader(entry.getKey(), entry.getValue()));

        return request;
    }

    @Override
    public Vertx vertx() {
        return this.vertx;
    }

    @Override
    public String auth() {
        return this.authorization;
    }

    @Override
    public HttpMethod method() {
        return this.method;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String urlWithQueryParams() {
        return buildUrlWithQueryParams(this.url, this.params);
    }

    @Override
    public String body() {
        return this.body;
    }

    @Override
    public void body(String body) {
        this.body = body;
    }

    @Override
    public void body(JsonObject body) {
        if (body != null) {
            this.body = body.encode();
        } else {
            this.body = null;
        }
    }

    @Override
    public String host() {
        return this.host;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public Handler<Response> handler() {
        return this.handler;
    }

    @Override
    public Handler<Response> errorHandler() {
        return this.errorHandler;
    }

    @Override
    public Handler<Response> statusCodeHandler(int statusCode) {
        return this.statusCodeHandlers.get(statusCode);
    }

    @Override
    public Map<String, Object> params() {
        return this.params;
    }

    @Override
    public RequestOptions handler(Handler<Response> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public RequestOptions errorHandler(Handler<Response> handler) {
        this.errorHandler = handler;
        return this;
    }

    @Override
    public RequestOptions statusCodeHandler(int statusCode, Handler<Response> handler) {
        this.statusCodeHandlers.put(statusCode, handler);
        return this;
    }

    @Override
    public List<RequestPlugin> plugins() {
        return this.plugins;
    }

    @Override
    public MultiMap headers() {
        return this.headers;
    }

    @Override
    public RequestOptions auth(String username, String password) {
        this.authorization = String.format("Basic %s", Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes()));
        return this;
    }

    @Override
    public RequestOptions accept(String accept) {
        this.set("Accept", accept);
        return this;
    }

    @Override
    public RequestOptions attach(String name, String path, String filename) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestOptions field(String name, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestOptions param(String key, Object value) {
        if (value == null) {
            this.params.remove(key);
        } else {
            this.params.put(key, value);
        }
        return this;
    }

    @Override
    public RequestOptions param(JsonObject params) {
        for (String key : params.fieldNames()) {
            param(key, params.getString(key));
        }

        return this;
    }

    @Override
    public RequestOptions redirects(int max) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestOptions send(String body) {
        this.body = body;
        return this;
    }

    @Override
    public RequestOptions send(JsonObject body) {
        this.body = body.encode();
        return this;
    }

    @Override
    public RequestOptions set(String key, String value) {
        if (!StringUtils.isBlank(value)) this.headers.set(key, value);
        return this;
    }

    @Override
    public RequestOptions timeout(long ms) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestOptions type(String type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestOptions use(RequestPlugin plugin) {
        this.plugins.add(plugin);
        return this;
    }

    @Override
    public RequestOptions write(Buffer data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestOptions host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public RequestOptions port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RequestOptions url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RequestOptions method(HttpMethod method) {
        this.method = method;
        return this;
    }

    private String buildUrlWithQueryParams(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);

        if ((sb.lastIndexOf("/") + 1) == sb.length()) {
            sb.deleteCharAt(sb.lastIndexOf("/"));
        }

        if (params.size() > 0) {
            StringBuilder qp = new StringBuilder();
            params.keySet().forEach(key -> {
                if (!StringUtils.isBlank(String.format("%s", params.get(key)))) {
                    qp.append(String.format("&%s=%s", key, params.get(key)));
                }
            });
            qp.replace(0, 1, "?");
            sb.append(qp);
        }

        return sb.toString();
    }
}
