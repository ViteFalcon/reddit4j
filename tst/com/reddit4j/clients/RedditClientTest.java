package com.reddit4j.clients;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.models.Subreddit;

public class RedditClientTest {

    private RedditClient redditClient;

    @Mock
    private ThrottledHttpClient mockThrottledHttpClient;

    @Mock
    private HttpMethod mockHttpMethod;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        redditClient = new RedditClient(mockThrottledHttpClient);
    }

    @Test
    public void testGetSubredditInfo() throws HttpException, IOException {
        when(mockThrottledHttpClient.get("/r/reddit4j/about.json")).thenReturn(mockHttpMethod);
        when(mockHttpMethod.getResponseBodyAsString()).thenReturn("{\"data\":{\"public_description\":\"Yay!\"}}");
        Subreddit subreddit = redditClient.getSubredditInfo("reddit4j");
        assertEquals("Yay!", subreddit.getPublicDescription());
        verify(mockHttpMethod, times(1)).releaseConnection();
    }
}
