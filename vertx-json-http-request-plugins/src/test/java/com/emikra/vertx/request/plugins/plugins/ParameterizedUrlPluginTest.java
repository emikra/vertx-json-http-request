package com.emikra.vertx.request.plugins.plugins;

import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.impl.DefaultRequestOptions;
import com.emikra.vertx.request.plugins.impl.ParameterizedUrlPlugin;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

public class ParameterizedUrlPluginTest extends VertxTestBase {

    private ParameterizedUrlPlugin plugin;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        this.plugin = new ParameterizedUrlPlugin();
    }

    @Test
    public void testParameters() {
        RequestOptions options = new DefaultRequestOptions(this.vertx);
        options.url("/:resource/:id");
        options.param("resource", "users");
        options.param("id", "12345");
        this.plugin.handle(options);

        assertEquals(options.urlWithQueryParams(), "/users/12345");
    }

    @Test
    public void testParametersWithAdditionalQueryParams() {
        RequestOptions options = new DefaultRequestOptions(this.vertx);
        options.url("/:resource/:id");
        options.param("resource", "users");
        options.param("id", "12345");
        options.param("a", "1");
        options.param("b", "2");
        this.plugin.handle(options);

        assertEquals(options.urlWithQueryParams(), "/users/12345?a=1&b=2");
    }

    @Test
    public void testNonStringQueryParams() {

        RequestOptions options = new DefaultRequestOptions(this.vertx);
        options.url("/:resource/:id");
        options.param("resource", "users");
        options.param("id", "12345");
        options.param("a", 1);
        options.param("b", "2");
        options.param("c", true);
        this.plugin.handle(options);

        assertEquals(options.urlWithQueryParams(), "/users/12345?a=1&b=2&c=true");
    }
}
