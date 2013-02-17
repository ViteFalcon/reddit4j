package com.reddit4j.models;

import org.joda.time.DateTime;

public abstract class VotableCreated extends RedditObject implements
		CreatedInterface, VotableInterface {
	private int ups;
	private int downs;
	private Boolean likes;
	private DateTime created;
	private DateTime createdUtc;

	@Override
	public int getUps() {
		return ups;
	}

	@Override
	public int getDowns() {
		return downs;
	}

	@Override
	public Boolean getLikes() {
		return likes;
	}

	@Override
	public DateTime getCreated() {
		return created;
	}

	@Override
	public DateTime getCreatedUtc() {
		return createdUtc;
	}

}
