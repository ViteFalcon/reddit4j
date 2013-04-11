package com.reddit4j.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;

import com.reddit4j.clients.RedditClient;
import com.reddit4j.models.Account;

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

}
