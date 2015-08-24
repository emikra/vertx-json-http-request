package com.emikra.vertx.request;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

@VertxGen
public interface Response {

    MultiMap headers();
    Boolean error();
    Boolean success();

    String bodyString();

    int status();

    default JsonObject bodyJsonObject() {
        return new JsonObject(bodyString());
    }

    default String contentType() {
        return headers().get("content-type");
    }

    default String getETag() {
        return headers().get("etag");
    }

    default String getLocation() {
        return headers().get("location");
    }

    JsonObject toJson();
}
