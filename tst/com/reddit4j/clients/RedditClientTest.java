package com.reddit4j.clients;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedditClientTest {
	
	private RedditJavaClient redditClient;

	@Before
	public void setUp() {
		redditClient = new RedditJavaClient();
	}
	
	@Test
	public void testGetSubredditInfo() {
		// INTEGRATION TEST WITH EXTERNAL (WEB) DEPENDENCIES - DO NOT MAKE PART OF REGULAR BUILD PROCESS
		String s = redditClient.getSubredditInfo("askscience");
		Assert.assertNotNull(s);
	}
}
