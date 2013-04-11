package com.reddit4j.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.reddit4j.clients.RedditClient;
import com.reddit4j.models.Account;
import com.reddit4j.models.Link;
import com.reddit4j.models.More;

public class AccountIntegrationTest {

    private RedditClient client = IntegrationRuntime.getClient();

    @Test
    public void testGetUserInfo_jedberg() throws IOException {
        Account jedberg = client.getUserInfo("jedberg");
        assertEquals("1wnj", jedberg.getId());
        assertEquals("jedberg", jedberg.getName());
    }

    @Test
    public void testIsUsernameAvailable_jedberg() throws IOException {
        assertFalse(client.isUsernameAvailable("jedberg"));
    }

    @Test
    public void testGetUserSubmissions_jedberg() throws IOException {
        More submissions = client.getUserSubmissions("jedberg");
        assertNotNull(submissions.getChildren());
        assertTrue(submissions.getChildren().size() > 3);
        assertEquals(Link.class, submissions.getChildren().get(0).getData().getClass());
        assertEquals("jedberg", ((Link) submissions.getChildren().get(0).getData()).getAuthor());
    }

}
