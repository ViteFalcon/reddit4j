package com.reddit4j.integration;

import java.util.Properties;

import org.junit.Test;

import com.reddit4j.clients.ClientFactory;
import com.reddit4j.clients.User;
import com.reddit4j.models.AuthenticationCredentials;

public class UserIntegrationTest {
    private Properties properties = IntegrationRuntime.getProperties();
    private ClientFactory factory = IntegrationRuntime.getClientFactory();
    private AuthenticationCredentials credentials = new AuthenticationCredentials(properties.getProperty("username"),
            properties.getProperty("password"));
    private User user = factory.newUser(credentials);

    @Test
    public void testPostComment() {
        // TODO Generate comment based on time/other criteria, then actually
        // validate that the Comment returned by this API is correct
        user.postComment(properties.getProperty("comment-post-id"), "test comment");
    }
}
