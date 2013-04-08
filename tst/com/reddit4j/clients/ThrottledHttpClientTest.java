package com.reddit4j.clients;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.exceptions.ThrottlingException;

public class ThrottledHttpClientTest {

    private static final int REQUEST_LIMIT_PER_PERIOD = 2;
    private static final int REQUEST_LIMIT_TIME_PERIOD_MS = 1 * 1000;
    private static final String SUCCESS = "success!";

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private ResponseHandler<String> mockResponseHandler;

    private ThrottledHttpClient throttledHttpClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        throttledHttpClient = new ThrottledHttpClient(mockHttpClient, mockResponseHandler, REQUEST_LIMIT_PER_PERIOD,
                REQUEST_LIMIT_TIME_PERIOD_MS);
    }

    @Test
    public void testSingleRequest() throws HttpException, IOException {
        when(mockHttpClient.execute(any(HttpUriRequest.class), eq(mockResponseHandler))).thenReturn(SUCCESS);
        assertEquals(SUCCESS, throttledHttpClient.execute(new HttpGet()));
    }

    @Test(expected = ThrottlingException.class)
    public void testLimitThrowsException() throws HttpException, IOException {
        when(mockHttpClient.execute(any(HttpUriRequest.class), eq(mockResponseHandler))).thenReturn(SUCCESS);
        for (int i = 0; i < REQUEST_LIMIT_PER_PERIOD + 1; i++) {
            assertEquals(SUCCESS, throttledHttpClient.execute(new HttpGet()));
        }
    }

    @Test
    public void testLimitExceptionHasNextTime() throws HttpException, IOException, InterruptedException {
        when(mockHttpClient.execute(any(HttpUriRequest.class), eq(mockResponseHandler))).thenReturn(SUCCESS);
        int attempts = 0;
        int success = 0;
        int sleeps = 0;
        for (int i = 0; i < REQUEST_LIMIT_PER_PERIOD * 3; i++) {
            try {
                attempts++;
                throttledHttpClient.execute(new HttpGet());
                success++;
            } catch (ThrottlingException te) {
                long millis = te.getNextValid().getMillis() - DateTime.now().getMillis();
                assertTrue(millis <= REQUEST_LIMIT_TIME_PERIOD_MS + 1);
                sleeps++;
                i--;
                Thread.sleep(millis);
            }
        }
        assertEquals(REQUEST_LIMIT_PER_PERIOD * 3, success);
        assertTrue(sleeps > 0);
        assertTrue(attempts > success);
    }

    @Test
    public void testGet_NullParams() throws HttpException, IOException {
        throttledHttpClient.get("test!", null);
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class), eq(mockResponseHandler));
    }
}
