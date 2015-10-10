package com.emikra.vertx.request;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.junit.Test;

public class RequestTest extends RequestTestBase {

    private static final Logger log = LoggerFactory.getLogger(RequestTest.class);

    private RequestFactory client;

    @Override
    public void setUp() throws Exception {

        super.setUp();

        this.client = RequestFactory.create(this.vertx);
        this.client.defaultOptions()
                .host("localhost")
                .port(9000);
    }

    @Test
    public void testVertxGet() {
        this.vertx.createHttpClient().request(HttpMethod.GET, 9000, "localhost", "/get", response -> {
            response.bodyHandler(body -> {
                testComplete();
            });
        }).end();
        await();
    }

    @Test
    public void testGet() {
        this.client.get("/get", response -> {
            testComplete();
        }).end();
        await();
    }

    @Test
    public void testGetBody() {
        this.client.get("/getBody", response -> {
            JsonObject json = response.bodyJsonObject();

            if (json.getInteger("a") == 1 && json.getInteger("b") == 2) {
                testComplete();
            } else {
                fail("Something is wrong with the json body...");
            }
        }).end();
        await();
    }

    @Test
    public void testSomething() {
        this.client.patch("/patch", new JsonObject(), response -> {
            testComplete();
        }).end();
        await();
    }

    @Test
    public void testPost() {
        this.client.post("/post", new JsonObject(), response -> {
            testComplete();
        }).end();
        await();
    }

    @Test
    public void testPut() {
        this.client.put("/put", new JsonObject(), response -> {
            testComplete();
        }).end();
        await();
    }

    @Test
    public void testDelete() {
        this.client.del("/delete", new JsonObject(), response -> {
            testComplete();
        }).end();
        await();
    }

    @Test
    public void testNotFound() {
        this.client.get("/notfound", response -> {
            if (response.status() == 404) {
                testComplete();
            } else {
                fail("This route does not exist so should return not found (404)");
            }
        }).end();
        await();
    }

    @Test
    public void testErrorHandler() {
        this.client.get("/notfound", response -> {
            fail("There is an error handler set, that should be called.");
        }).errorHandler(error -> {
            testComplete();
        }).end();
        await();
    }

    @Test
    public void testQueryParameters() {
        Request request = this.client.get("/get").param("a", "1").param("b", "2");
        assertEquals("/get?a=1&b=2", request.options().urlWithQueryParams());
    }

    @Test
    public void testStatusCodeHandler200() {
        this.client.get("/get", response -> {
            fail("It should call the statusCode Handler");
        }).handler(200, success -> {
            testComplete();
        }).errorHandler(error -> {
            fail("Should not fail");
        }).end();

        await();
    }

    @Test
    public void testStatusCodeHandler404() {
        this.client.get("/notfound", response -> {
            fail("Should call the 404 statusCode Handler");
        }).handler(200, success -> {
            fail("Should call the 404 statusCode Handler");
        }).handler(404, notfound -> {
            testComplete();
        }).errorHandler(error -> {
            fail("Should call the 404 statusCode Handler");
        }).end();

        await();
    }

    @Test
    public void testEmptyBody() {

        this.client.get("/nobody", response -> {
            JsonObject jsonResponse = response.toJson();
            testComplete();
        }).end();

        await();
    }

}
