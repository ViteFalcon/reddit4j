package com.reddit4j.clients;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
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
    
    @Mock
    private PostMethod mockPostMethod;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        redditClient = new RedditClient(mockThrottledHttpClient);
    }

    @Test
    public void testGetSubredditInfo() throws HttpException, IOException {
        when(mockThrottledHttpClient.get("/r/reddit4j/about.json", null)).thenReturn(mockHttpMethod);
        when(mockHttpMethod.getResponseBodyAsString()).thenReturn("{\"data\":{\"public_description\":\"Yay!\"}}");
        Subreddit subreddit = redditClient.getSubredditInfo("reddit4j");
        assertEquals("Yay!", subreddit.getPublicDescription());
        verify(mockHttpMethod, times(1)).releaseConnection();
    }
    
    @Test
    public void testPostComment() throws HttpException, IOException {
        when(mockThrottledHttpClient.post(eq("/api/comment"), any(NameValuePair[].class))).thenReturn(mockPostMethod);
        when(mockPostMethod.getResponseBodyAsString()).thenReturn("{\"data\":{}}");
        redditClient.postComment("this is a test comment", "test-parent-id", "modhash");
        verify(mockPostMethod, times(1)).releaseConnection();
    }
}
