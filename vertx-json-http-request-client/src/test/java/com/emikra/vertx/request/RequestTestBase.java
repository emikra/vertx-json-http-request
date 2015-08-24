package com.emikra.vertx.request;

import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.test.core.VertxTestBase;

import java.util.concurrent.CountDownLatch;

public class RequestTestBase extends VertxTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        HttpServer server = this.vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.get("/get").handler(ctx -> {
            ctx.response().setStatusCode(200).end();
        });

        router.get("/getBody").handler(ctx -> {
            JsonObject json = new JsonObject();
            json.put("a", 1);
            json.put("b", 2);

            ctx.response().setStatusCode(200).end(json.encode());
        });

        router.patch("/patch").handler(ctx -> {
            ctx.response().setStatusCode(200).end();
        });

        router.post("/post").handler(ctx -> {
            ctx.response().setStatusCode(200).end();
        });

        router.put("/put").handler(ctx -> {
            ctx.response().setStatusCode(200).end();
        });

        router.delete("/delete").handler(ctx -> {
            ctx.response().setStatusCode(200).end();
        });

        server.requestHandler(router::accept);

        CountDownLatch finished = new CountDownLatch(1);

        server.listen(9000, setup -> {
            finished.countDown();
        });

        awaitLatch(finished);
    }
}
