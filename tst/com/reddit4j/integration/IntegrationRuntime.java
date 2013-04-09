package com.reddit4j.integration;

import com.reddit4j.clients.RedditClient;

public class IntegrationRuntime {
    private static RedditClient client = new RedditClient();

    private IntegrationRuntime() {
    }

    public static RedditClient getClient() {
        return client;
    }

}
