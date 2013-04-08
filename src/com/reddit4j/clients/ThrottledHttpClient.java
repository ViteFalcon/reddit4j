package com.reddit4j.clients;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reddit4j.exceptions.ThrottlingException;

/**
 * Number-of-requests-per-period throttling on HttpClient. Throws a
 * {@code ThrottlingException} when too many requests have been tried per
 * period. The {@code ThrottlingException} contains the {@code DateTime} when
 * the next request will be allowed.
 * 
 */
public class ThrottledHttpClient {

    public static final int DEFAULT_REQUEST_LIMIT_PER_PERIOD = 30;
    public static final int DEFAULT_REQUEST_LIMIT_TIME_PERIOD_MS = 60 * 1000;

    private Queue<DateTime> sentRequestTimestamps = new LinkedList<DateTime>();
    private HttpClient httpClient;
    private ResponseHandler<String> responseHandler;
    private final int REQUEST_LIMIT_PER_PERIOD;
    private final int REQUEST_LIMIT_TIME_PERIOD_MS;
    private final Logger logger = LoggerFactory.getLogger(ThrottledHttpClient.class);

    public ThrottledHttpClient() {
        this.httpClient = new DefaultHttpClient();
        this.responseHandler = new BasicResponseHandler();
        this.REQUEST_LIMIT_PER_PERIOD = DEFAULT_REQUEST_LIMIT_PER_PERIOD;
        this.REQUEST_LIMIT_TIME_PERIOD_MS = DEFAULT_REQUEST_LIMIT_TIME_PERIOD_MS;
    }

    /*
     * For unit testing
     */
    protected ThrottledHttpClient(HttpClient httpClient, ResponseHandler<String> responseHandler, int requestLimit,
            int periodMs) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
        this.REQUEST_LIMIT_PER_PERIOD = requestLimit;
        this.REQUEST_LIMIT_TIME_PERIOD_MS = periodMs;
    }

    protected String execute(HttpUriRequest request) throws ClientProtocolException, IOException {
        drainQueue();
        if (sentRequestTimestamps.size() >= REQUEST_LIMIT_PER_PERIOD) {
            logger.info("Cannot make request, exceeds {} requests per {} ms", REQUEST_LIMIT_PER_PERIOD,
                    REQUEST_LIMIT_TIME_PERIOD_MS);
            throw new ThrottlingException(sentRequestTimestamps.peek(), REQUEST_LIMIT_TIME_PERIOD_MS + 1);
        }
        sentRequestTimestamps.add(DateTime.now());
        return httpClient.execute(request, responseHandler);
    }

    /**
     * HTTP GET request
     * 
     * @param uri
     * @param params
     * @return a String which is the Body of the request. This only works for
     *         200 OK responses.
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String get(String uri, HttpParams params) throws ClientProtocolException, IOException {
        HttpGet getRequest = new HttpGet(uri);
        if (params != null) {
            getRequest.setParams(params);
        }
        return execute(getRequest);
    }

    /**
     * HTTP POST request
     * 
     * @param uri
     * @param params
     * @return a String which is the Body of the request. This only works for
     *         200 OK responses.
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String post(String uri, HttpParams params) throws ClientProtocolException, IOException {
        HttpPost postRequest = new HttpPost(uri);
        if (params != null) {
            postRequest.setParams(params);
        }
        return execute(postRequest);
    }

    /*
     * When called, drain the queue of any messages older than
     * REQUEST_LIMIT_TIME_PERIOD_MS
     */
    private synchronized void drainQueue() {
        while (sentRequestTimestamps.size() > 0) {
            DateTime oldest = sentRequestTimestamps.peek();
            if (oldest == null || DateTime.now().compareTo(oldest.plusMillis(REQUEST_LIMIT_TIME_PERIOD_MS)) > 0) {
                // evict request from history
                sentRequestTimestamps.remove();
            } else {
                break;
            }
        }
    }
}
