package com.reddit4j.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.reddit4j.clients.RedditClient;
import com.reddit4j.models.Subreddit;

public class SubredditInfoIntegrationTest {

    private RedditClient client = IntegrationRuntime.getClient();

    /*
     * Since these are integration tests, we can only assert against values we
     * are relatively certain will not change.
     */

    @Test
    public void testPublicDefault_Pics() throws IOException {
        Subreddit pics = client.getSubredditInfo("pics");
        assertEquals("/r/pics/", pics.getUrl());
        assertEquals("2qh0u", pics.getId());
    }

    @Test
    public void testPublic_Java() throws IOException {
        Subreddit java = client.getSubredditInfo("java");
        assertEquals("/r/java/", java.getUrl());
        assertEquals("2qhd7", java.getId());
    }
}
