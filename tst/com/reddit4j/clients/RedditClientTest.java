package com.reddit4j.clients;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.params.HttpParams;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.models.Subreddit;

public class RedditClientTest {

    private RedditClient redditClient;

    @Mock
    private ThrottledHttpClient mockThrottledHttpClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        redditClient = new RedditClient(mockThrottledHttpClient);
    }

    @Test
    public void testGetSubredditInfo() throws HttpException, IOException {
        when(mockThrottledHttpClient.get("http://reddit.com/r/reddit4j/about.json", null)).thenReturn(
                "{\"data\":{\"public_description\":\"Yay!\"}}");
        Subreddit subreddit = redditClient.getSubredditInfo("reddit4j");
        assertEquals("Yay!", subreddit.getPublicDescription());
    }

    @Test
    public void testPostComment() throws HttpException, IOException {
        redditClient.postComment("this is a test comment", "test-parent-id", "modhash");
        verify(mockThrottledHttpClient, times(1)).post(eq("http://reddit.com/api/comment"), any(HttpParams.class));
    }
}
