package com.emikra.vertx.request.plugins.plugins;

import com.emikra.vertx.request.RequestOptions;
import com.emikra.vertx.request.impl.DefaultRequestOptions;
import com.emikra.vertx.request.plugins.impl.PrefixUrlPlugin;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

public class PrefixPluginTest extends VertxTestBase {

    private PrefixUrlPlugin plugin;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        this.plugin = new PrefixUrlPlugin("/api/v1");
    }

    @Test
    public void testPrefix() {

        RequestOptions options = new DefaultRequestOptions(this.vertx);
        options.url("/users/12345");

        this.plugin.handle(options);

        assertEquals(options.url(), "/api/v1/users/12345");
    }
}
