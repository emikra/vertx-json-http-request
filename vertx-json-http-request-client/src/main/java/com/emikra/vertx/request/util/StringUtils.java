package com.emikra.vertx.request.util;

public class StringUtils {

    public static Boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
