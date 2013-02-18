package com.reddit4j.clients;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
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

	// TODO: Generalize this a bit. Right now these throttles are for reddit's
	// rate-limiting, but this could easily be made generic
	public static final int REQUEST_LIMIT_PER_PERIOD = 30;
	public static final int REQUEST_LIMIT_TIME_PERIOD_MS = 60 * 1000;
	private static final int TIMER_INTERVAL_MS = 2 * 1000;
	private static final int TIMER_DELAY_MS = 0;

	private LinkedList<DateTime> sentRequestTimestamps = new LinkedList<DateTime>();
	private Timer timer;
	private HttpClient httpClient;

	public ThrottledHttpClient() {
		this.httpClient = new HttpClient();
		initTimer();
	}

	/*
	 * For unit testing
	 */
	protected ThrottledHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
		initTimer();
	}

	public int executeMethod(HttpMethod method) throws HttpException,
			IOException {
		if (sentRequestTimestamps.size() > REQUEST_LIMIT_PER_PERIOD) {
			throw new ThrottlingException(sentRequestTimestamps.getFirst()
					.plusMillis(REQUEST_LIMIT_TIME_PERIOD_MS));
		}
		return httpClient.executeMethod(method);
	}

	private void initTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tick();
			}
		}, TIMER_DELAY_MS, TIMER_INTERVAL_MS);
	}

	/*
	 * On each tick (TIMER_INTERVAL_MS apart), drain each request from the
	 * request queue that is at least REQUEST_LIMIT_TIME_PERIOD_MS old.
	 */
	private synchronized void tick() {
		while (sentRequestTimestamps.size() > 0) {
			DateTime oldest = sentRequestTimestamps.getFirst();
			if (oldest == null
					|| DateTime.now().compareTo(
							oldest.plusMillis(REQUEST_LIMIT_TIME_PERIOD_MS)) > 0) {
				// evict request from history
				sentRequestTimestamps.removeFirst();
			} else {
				break;
			}
		}
	}
}
