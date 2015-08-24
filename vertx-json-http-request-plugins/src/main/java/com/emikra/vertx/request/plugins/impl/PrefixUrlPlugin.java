package com.emikra.vertx.request.plugins.impl;

import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.plugins.RequestPlugin;

public class PrefixUrlPlugin implements RequestPlugin {

    private String prefix;

    public PrefixUrlPlugin(String prefix) {
        this.prefix = prefix;
    }
    @Override
    public void handle(RequestOptions options) {
        String url = this.prefix + options.url();
        options.url(url);
    }
}
