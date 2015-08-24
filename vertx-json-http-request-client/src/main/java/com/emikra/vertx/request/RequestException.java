package com.emikra.vertx.request;

public class RequestException extends RuntimeException {

    public RequestException(Throwable e) {
        super(e);
    }

    public RequestException(String message) {
        super(message);
    }
}
