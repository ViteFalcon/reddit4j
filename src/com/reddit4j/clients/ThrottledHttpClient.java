package com.reddit4j.clients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
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

    public static final String REGULAR_SCHEME = "http";
    public static final String SECURE_SCHEME = "https";
    public static final String DEFAULT_ENCODING = "UTF-8";

    private Queue<DateTime> sentRequestTimestamps = new LinkedList<DateTime>();
    private final HttpClient httpClient;
    private final ResponseHandler<String> responseHandler;
    private HttpParams httpParams;
    private final int REQUEST_LIMIT_PER_PERIOD;
    private final int REQUEST_LIMIT_TIME_PERIOD_MS;
    private final Logger logger = LoggerFactory.getLogger(ThrottledHttpClient.class);

    public ThrottledHttpClient() {
        this.httpClient = new DefaultHttpClient();
        this.responseHandler = new BasicResponseHandler();
        this.httpParams = new SyncBasicHttpParams();
        this.REQUEST_LIMIT_PER_PERIOD = DEFAULT_REQUEST_LIMIT_PER_PERIOD;
        this.REQUEST_LIMIT_TIME_PERIOD_MS = DEFAULT_REQUEST_LIMIT_TIME_PERIOD_MS;
    }

    /*
     * For unit testing
     */
    protected ThrottledHttpClient(HttpClient httpClient, ResponseHandler<String> responseHandler,
            HttpParams httpParams, int requestLimit, int periodMs) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
        this.httpParams = httpParams;
        this.REQUEST_LIMIT_PER_PERIOD = requestLimit;
        this.REQUEST_LIMIT_TIME_PERIOD_MS = periodMs;
    }

    protected void setUserAgent(String userAgent) {
        httpParams.setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
    }

    protected String execute(HttpUriRequest request) throws ClientProtocolException, IOException {
        drainQueue();
        if (sentRequestTimestamps.size() >= REQUEST_LIMIT_PER_PERIOD) {
            logger.info("Cannot make request, exceeds {} requests per {} ms", REQUEST_LIMIT_PER_PERIOD,
                    REQUEST_LIMIT_TIME_PERIOD_MS);
            throw new ThrottlingException(sentRequestTimestamps.peek(), REQUEST_LIMIT_TIME_PERIOD_MS + 1);
        }
        sentRequestTimestamps.add(DateTime.now());
        if (httpParams != null) {
            request.setParams(httpParams);
        }
        return httpClient.execute(request, responseHandler);
    }

    /**
     * HTTP GET request
     * 
     * @param secure
     *            - should the request be http or https
     * @param host
     * @param path
     *            - /something
     * @param formParams
     *            - parameters to append to the end of the URI, eg:
     *            ?param1=value1&param2=value2
     * @return a String which is the Body of the request. This only works for
     *         200 OK responses.
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    protected String get(boolean secure, String host, String path, List<NameValuePair> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        URIBuilder builder = new URIBuilder().setScheme(secure ? SECURE_SCHEME : REGULAR_SCHEME).setHost(host)
                .setPath(path);
        if (formParams != null) {
            for (NameValuePair param : formParams) {
                builder.setParameter(param.getName(), param.getValue());
            }
        }
        HttpGet getRequest = new HttpGet(builder.build());
        return execute(getRequest);
    }

    /**
     * HTTP POST request
     * 
     * @param secure
     *            - should the request be http or https
     * @param host
     * @param path
     *            - /something
     * @param formParams
     *            - POSTed parameters
     * @return a String which is the Body of the request. This only works for
     *         200 OK responses.
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String post(boolean secure, String host, String path, List<NameValuePair> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        URIBuilder builder = new URIBuilder().setScheme(secure ? SECURE_SCHEME : REGULAR_SCHEME).setHost(host)
                .setPath(path);
        HttpPost postRequest = new HttpPost(builder.build());
        if (formParams != null) {
            postRequest.setEntity(new UrlEncodedFormEntity(formParams, DEFAULT_ENCODING));
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
