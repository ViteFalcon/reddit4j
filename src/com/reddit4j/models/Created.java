package com.reddit4j.models;

import org.joda.time.DateTime;

public abstract class Created extends RedditObject implements CreatedInterface {

	private DateTime created;
	private DateTime createdUtc;

	@Override
	public DateTime getCreated() {
		return created;
	}

	@Override
	public DateTime getCreatedUtc() {
		return createdUtc;
	}

}
