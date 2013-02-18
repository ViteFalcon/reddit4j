package com.reddit4j.clients;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.joda.time.DateTime;

// TODO rename
public class RedditJavaClient {
	// TODO give these better names
	public static final int REQUEST_LIMIT = 30;
	public static final int REQUEST_LIMIT_TIME_PERIOD = 60 * 1000;
	
	private LinkedList<DateTime> sentRequestTimestamps = new LinkedList<DateTime>();
	private HttpClient httpClient;
	private Timer timer;
	
	public RedditJavaClient() {
		// default dummy/empty constructor
		HostConfiguration h = new HostConfiguration();
		
		// TODO set User-Agent string
		h.setHost("reddit.com");
		httpClient = new HttpClient();
		httpClient.setHostConfiguration(h);
		//h.
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tick();	
			}
		}, 0, 2 * 1000);
	}
	
	// TODO when ready, refactor this to return POJO instead of JSON string
	public String getSubredditInfo(String subreddit) {
		// TODO make factory for Http methods
		// TODO add headers/footers to method
		HttpMethod method = new GetMethod(String.format("/r/%s/about.json", subreddit));
		tryRequest(method);
		try {
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private int tryRequest(HttpMethod method) {
		if(sentRequestTimestamps.size() > REQUEST_LIMIT) {
			// let's keep this simple and make the whole client synchronous
			// in this case we'll just say the request failed and have our consumers redrive
			// actually, the httpClient is configured by default to drive requests with non-fatal errors 3 times
			return -1; 
		}
		
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(method);
			//new BasicHttpResponse();
			//httpClient.
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = -1;
		}
		sentRequestTimestamps.addLast(DateTime.now());
		method.releaseConnection();
		return statusCode;
	}
	
	private synchronized void tick() {
		while(sentRequestTimestamps.size() > 0) {
			DateTime oldest = sentRequestTimestamps.getFirst(); 
			if(oldest == null || DateTime.now().compareTo(oldest.plusMinutes(1)) > 0) {
				// evict request from history
				sentRequestTimestamps.removeFirst();
			} else {
				break;
			}
		}
	}
}

