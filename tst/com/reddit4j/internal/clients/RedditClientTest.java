package com.reddit4j.internal.clients;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.exceptions.Reddit4jException;
import com.reddit4j.exceptions.RedditAuthenticationException;
import com.reddit4j.internal.models.AuthenticationResults;
import com.reddit4j.internal.models.Subreddit;

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
    public void testGetSubredditInfo() throws HttpException, IOException, URISyntaxException {
        when(mockThrottledHttpClient.get(false, "www.reddit.com", "/r/reddit4j/about.json", null)).thenReturn(
                "{\"data\":{\"public_description\":\"Yay!\"}}");
        Subreddit subreddit = redditClient.getSubredditInfo("reddit4j");
        assertEquals("Yay!", subreddit.getPublicDescription());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPostComment() throws HttpException, IOException, URISyntaxException {
        redditClient.postComment("this is a test comment", "test-parent-id", "modhash", null);
        verify(mockThrottledHttpClient, times(1)).post(eq(false), eq("www.reddit.com"), eq("/api/comment"),
                any(List.class), eq((HttpContext) null));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Reddit4jException.class)
    public void testGetSubredditInfo_ClientException() throws ClientProtocolException, URISyntaxException, IOException {
        when(mockThrottledHttpClient.get(anyBoolean(), anyString(), anyString(), any(List.class))).thenThrow(
                new ClientProtocolException());
        redditClient.getSubredditInfo("fail");
    }

    @Test
    public void testLogin_successful() throws ClientProtocolException, URISyntaxException, IOException {
        String jsonAuthResults = "{\"json\":{\"data\":{\"cookie\":\"cookieval\"}}}";
        @SuppressWarnings("serial")
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("user", "username"));
                add(new BasicNameValuePair("passwd", "password"));
                add(new BasicNameValuePair("api_type", "json"));
            }
        };
        HttpContext context = new BasicHttpContext();
        when(mockThrottledHttpClient.post(eq(true), eq("ssl.reddit.com"), eq("/api/login"), eq(params), eq(context)))
                .thenReturn(jsonAuthResults);

        AuthenticationResults result = redditClient.login("username", "password", context);
        assertEquals("cookieval", result.getCookie());
    }

    @Test(expected = RedditAuthenticationException.class)
    public void testLogin_failure() throws ClientProtocolException, URISyntaxException, IOException {
        String jsonAuthResults = "{\"json\":{\"errors\":[[\"WRONG_PASSWORD\"]]}}";
        @SuppressWarnings("serial")
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("user", "username"));
                add(new BasicNameValuePair("passwd", "password"));
                add(new BasicNameValuePair("api_type", "json"));
            }
        };
        when(
                mockThrottledHttpClient.post(eq(true), eq("ssl.reddit.com"), eq("/api/login"), eq(params),
                        eq((HttpContext) null))).thenReturn(jsonAuthResults);

        redditClient.login("username", "password", null);
    }

    // TODO: Test other exceptions. May require PowerMock-Mockito to spy on
    // class members
}
