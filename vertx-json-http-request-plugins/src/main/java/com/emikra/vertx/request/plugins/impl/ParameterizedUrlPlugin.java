package com.emikra.vertx.request.plugins.impl;

import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.plugins.RequestPlugin;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterizedUrlPlugin implements RequestPlugin {

    public ParameterizedUrlPlugin() {

    }

    @Override
    public void handle(RequestOptions options) {
        options.url(generateUrl(options.url(), options.params()));
    }

    private String generateUrl(String url, Map<String, Object> params) {
        Matcher m = Pattern.compile(":([A-Za-z][A-Za-z0-9_]*)").matcher(url);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String key = m.group().substring(1);
            if (params.containsKey(key)) {
                m.appendReplacement(sb, String.format("%s", params.get(key)));
                params.remove(key);
            } else {
                throw new RuntimeException(String.format("Missing parameter ':%s'", key));
            }
        }

        m.appendTail(sb);
        return sb.toString();
    }
}
