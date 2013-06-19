package com.reddit4j.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.reddit4j.internal.clients.RedditClient;

public class IntegrationRuntime {
    private static RedditClient client;
    private static Properties properties;

    private IntegrationRuntime() {
    }

    public static RedditClient getClient() {
        // lazy initialize, since unit tests running alone don't need this
        if (client == null) {
            client = new RedditClient("reddit4j integration test runner - 0.01");
        }
        return client;
    }

    public static Properties getProperties() {
        // lazy initialize, since unit tests running alone don't need this
        if (properties == null) {
            properties = new Properties();
            InputStream propertyStream = IntegrationRuntime.class.getResourceAsStream("integration.properties");
            try {
                properties.load(propertyStream);
                propertyStream.close();
            } catch (IOException e) {
                // re-throw as a runtime exception so we don't have to
                // explicitly catch IOException every time we want to get
                // properties for integration tests
                throw new RuntimeException("Could not read integration.properties", e);
            }
        }
        return properties;
    }

}
