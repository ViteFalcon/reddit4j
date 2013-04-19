package com.reddit4j.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.reddit4j.clients.RedditClient;
import com.reddit4j.models.Account;
import com.reddit4j.models.Comment;
import com.reddit4j.models.Link;
import com.reddit4j.models.More;
import com.reddit4j.models.RedditThing;

public class AccountIntegrationTest {

    private RedditClient client = IntegrationRuntime.getClient();

    private static final String JEDBERG = "jedberg";

    @Test
    public void testGetUserInfo_jedberg() throws IOException {
        Account jedberg = client.getUserInfo(JEDBERG);
        assertEquals("1wnj", jedberg.getId());
        assertEquals("jedberg", jedberg.getName());
    }

    @Test
    public void testIsUsernameAvailable_jedberg() throws IOException {
        assertFalse(client.isUsernameAvailable(JEDBERG));
    }

    @Test
    public void testGetUserSubmissions_jedberg() throws IOException {
        More submissions = client.getUserSubmissions(JEDBERG);
        assertNotNull(submissions.getChildren());
        assertTrue(submissions.getChildren().size() > 3);
        for (RedditThing thing : submissions.getChildren()) {
            assertEquals(Link.class, thing.getData().getClass());
            assertEquals("jedberg", ((Link) thing.getData()).getAuthor());
        }
    }

    @Test
    public void testGetUserOverview_jedberg() throws IOException {
        More overviewEntries = client.getUserOverview(JEDBERG);
        assertNotNull(overviewEntries.getChildren());
        assertTrue(overviewEntries.getChildren().size() > 3);
        for (RedditThing thing : overviewEntries.getChildren()) {
            assertTrue(Link.class == thing.getData().getClass() || Comment.class == thing.getData().getClass());
        }
    }

    @Test
    public void testGetUserComments_jedberg() throws IOException {
        More comments = client.getUserComments(JEDBERG);
        assertNotNull(comments.getChildren());
        assertTrue(comments.getChildren().size() > 3);
        for (RedditThing thing : comments.getChildren()) {
            assertEquals(Comment.class, thing.getData().getClass());
            assertEquals("jedberg", ((Comment) thing.getData()).getAuthor());
        }
    }
}
