package com.reddit4j.clients;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.internal.clients.RedditClient;
import com.reddit4j.internal.models.More;
import com.reddit4j.internal.models.RedditThing;
import com.reddit4j.models.AuthenticationCredentials;
import com.reddit4j.types.MessageFolder;

public class UserTest {

    @Mock
    private RedditClient mockRedditClient;

    @Mock
    private AuthenticationCredentials mockAuthenticationCredentials;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = new User(mockRedditClient, mockAuthenticationCredentials);

    }

    @Test
    public void verifyLogin() {
        verify(mockRedditClient, times(1)).login(anyString(), anyString(), any(HttpContext.class));
    }

    @Test
    public void testGetInbox() {
        String previous = "previous";
        int max = 4;
        RedditThing thing = new RedditThing();
        More more = new More();
        more.setChildren(new ArrayList<RedditThing>());
        thing.setData(more);
        when(mockRedditClient.getMessages(any(MessageFolder.class), anyString(), anyInt(), any(HttpContext.class)))
                .thenReturn(thing);

        user.getInbox(previous, max);

        verify(mockRedditClient, times(1)).getMessages(eq(MessageFolder.Inbox), eq(previous), eq(max),
                any(HttpContext.class));
    }
}
