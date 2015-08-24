package com.emikra.vertx.request.plugins.impl;

import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.Response;
import com.emikra.vertx.request.plugins.RequestPlugin;

public class LogPlugin implements RequestPlugin {

    @Override
    public void handle(RequestOptions options) {
        System.out.println("==========================================");
        System.out.println(String.format("= Request (%s)", options.method()));
        System.out.println(String.format("= Host: %s, Port: %d", options.host(), options.port()));
        System.out.println(String.format("= URL: %s | %s", options.url(), options.urlWithQueryParams()));
        System.out.println(String.format("= Body: %s", options.body()));
        System.out.println("==========================================");

    }

    @Override
    public void handleResponse(Response response) {
        System.out.println("==========================================");
        System.out.println(String.format("= Response (Status %d)", response.status()));
        System.out.println(String.format("= Body: %s", response.bodyString()));
        System.out.println("==========================================");
    }
}
