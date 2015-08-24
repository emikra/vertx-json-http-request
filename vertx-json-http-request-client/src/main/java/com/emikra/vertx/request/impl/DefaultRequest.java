package com.emikra.vertx.request.impl;

import com.emikra.vertx.request.Request;
import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.util.StringUtils;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


public class DefaultRequest implements Request {

    private static final Logger log = LoggerFactory.getLogger(DefaultRequest.class);

    private RequestOptions options;

    public DefaultRequest(DefaultRequest request) {
        this.options = new DefaultRequestOptions(request.options.copy());
    }

    public DefaultRequest(RequestOptions options) {
        this.options = options.copy();
    }

    @Override
    public RequestOptions options() {
        return this.options;
    }

    @Override
    public void abort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void end() {

        this.options.plugins().forEach(plugin -> plugin.handle(this.options));

        HttpClientRequest request = this.options.getVertxRequest();

        if (StringUtils.isBlank(this.options.body())) {
            request.end();
        } else {
            request.end(this.options.body());
        }
    }

}
