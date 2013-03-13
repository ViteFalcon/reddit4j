package com.reddit4j.clients;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.exceptions.ThrottlingException;

public class ThrottledHttpClientTest {

    private static final int REQUEST_LIMIT_PER_PERIOD = 2;
    private static final int REQUEST_LIMIT_TIME_PERIOD_MS = 1 * 1000;

    @Mock
    private HttpClient mockHttpClient;

    private ThrottledHttpClient throttledHttpClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        throttledHttpClient = new ThrottledHttpClient(mockHttpClient, REQUEST_LIMIT_PER_PERIOD,
                REQUEST_LIMIT_TIME_PERIOD_MS);
    }

    @Test
    public void testSingleRequest() throws HttpException, IOException {
        when(mockHttpClient.executeMethod(any(HttpMethod.class))).thenReturn(200);
        assertEquals(200, throttledHttpClient.executeMethod(new GetMethod()));
    }

    @Test(expected = ThrottlingException.class)
    public void testLimitThrowsException() throws HttpException, IOException {
        when(mockHttpClient.executeMethod(any(HttpMethod.class))).thenReturn(200);
        for (int i = 0; i < REQUEST_LIMIT_PER_PERIOD + 1; i++) {
            assertEquals(200, throttledHttpClient.executeMethod(new GetMethod()));
        }
    }

    @Test
    public void testLimitExceptionHasNextTime() throws HttpException, IOException, InterruptedException {
        when(mockHttpClient.executeMethod(any(HttpMethod.class))).thenReturn(200);
        int attempts = 0;
        int success = 0;
        int sleeps = 0;
        for (int i = 0; i < REQUEST_LIMIT_PER_PERIOD * 3; i++) {
            try {
                attempts++;
                throttledHttpClient.executeMethod(new GetMethod());
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
    public void testGet() throws HttpException, IOException {
        throttledHttpClient.get("test!");
        verify(mockHttpClient, times(1)).executeMethod(any(GetMethod.class));
    }
}
