package com.reddit4j.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Properties;

import org.junit.Test;

import com.reddit4j.clients.ClientFactory;
import com.reddit4j.clients.User;
import com.reddit4j.models.AuthenticationCredentials;
import com.reddit4j.models.MailboxPage;

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

    @Test
    public void testGetInbox_page1() {
        MailboxPage page = user.getInbox(null, 25);
        assertNotNull(page);
        assertNull(page.getPreviousId());
    }

    @Test
    public void testGetInbox_pagination() {
        MailboxPage page1 = user.getInbox(null, 1);
        assertNotNull(page1);
        if (page1.getLastId() != null) {
            MailboxPage page2 = user.getInbox(page1.getLastId(), 1);
            assertNotNull(page2);
            assertEquals(page1.getLastId(), page2.getPreviousId());
        }
    }
}
