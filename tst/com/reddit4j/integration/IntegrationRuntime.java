package com.reddit4j.integration;

import com.reddit4j.clients.RedditClient;

public class IntegrationRuntime {
    private static RedditClient client = new RedditClient("reddit4j integration test runner - 0.01");

    private IntegrationRuntime() {
    }

    public static RedditClient getClient() {
        return client;
    }

}
