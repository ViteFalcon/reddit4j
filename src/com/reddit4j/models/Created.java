package com.reddit4j.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class Created extends RedditObject implements CreatedInterface {

    private DateTime created;

    @JsonProperty("created_utc")
    private DateTime createdUtc;

    protected void setCreated(long seconds) {
        this.created = new DateTime(seconds * 1000, DateTimeZone.UTC);
    }

    protected void setCreatedUtc(long seconds) {
        this.createdUtc = new DateTime(seconds * 1000, DateTimeZone.UTC);
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
