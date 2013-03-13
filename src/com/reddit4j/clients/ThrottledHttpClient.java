package com.reddit4j.clients;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.joda.time.DateTime;

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
    private final int REQUEST_LIMIT_PER_PERIOD;
    private final int REQUEST_LIMIT_TIME_PERIOD_MS;

    public ThrottledHttpClient() {
        this.httpClient = new HttpClient();
        this.REQUEST_LIMIT_PER_PERIOD = DEFAULT_REQUEST_LIMIT_PER_PERIOD;
        this.REQUEST_LIMIT_TIME_PERIOD_MS = DEFAULT_REQUEST_LIMIT_TIME_PERIOD_MS;
    }

    /*
     * For unit testing
     */
    protected ThrottledHttpClient(HttpClient httpClient, int requestLimit, int periodMs) {
        this.httpClient = httpClient;
        this.REQUEST_LIMIT_PER_PERIOD = requestLimit;
        this.REQUEST_LIMIT_TIME_PERIOD_MS = periodMs;
    }

    public void setHostConfiguration(HostConfiguration hostConfiguration) {
        httpClient.setHostConfiguration(hostConfiguration);
    }

    public int executeMethod(HttpMethod method) throws HttpException, IOException {
        drainQueue();
        if (sentRequestTimestamps.size() >= REQUEST_LIMIT_PER_PERIOD) {
            throw new ThrottlingException(sentRequestTimestamps.peek(), REQUEST_LIMIT_TIME_PERIOD_MS + 1);
        }
        sentRequestTimestamps.add(DateTime.now());
        return httpClient.executeMethod(method);
    }

    /**
     * HTTP GET request
     * 
     * @param uri
     * @return a HttpMethod object. Remember to call method.releaseConnection()
     *         when you're done!
     * @throws HttpException
     * @throws IOException
     */
    public HttpMethod get(String uri) throws HttpException, IOException {
        HttpMethod method = new GetMethod(uri);
        executeMethod(method);
        return method;
    }

    /*
     * When called, drain the queue of any messages older than
     * REQUEST_LIMIT_TIME_PERIOD_MS
     */
    private void drainQueue() {
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
