package com.reddit4j.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class VotableCreated extends RedditObject implements CreatedInterface, VotableInterface {

    private int ups;

    private int downs;

    private Boolean likes;

    private DateTime created;

    @JsonProperty("created_utc")
    private DateTime createdUtc;

    public void setCreated(long seconds) {
        this.created = new DateTime(seconds * 1000, DateTimeZone.UTC);
    }

    public void setCreatedUtc(long seconds) {
        this.createdUtc = new DateTime(seconds * 1000, DateTimeZone.UTC);
    }

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
