package com.emikra.vertx.request;

import com.emikra.vertx.request.util.StringUtils;
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
        JsonObject response = null;

        try {
            response = new JsonObject(bodyString());
        } catch (Exception e) {
            throw new RuntimeException("There was an issue decoding the response body to json.");
        }

        return response;
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
