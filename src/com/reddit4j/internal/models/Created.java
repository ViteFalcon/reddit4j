package com.reddit4j.internal.models;

import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.reddit4j.internal.json.DateTimeLongSerializer;

@EqualsAndHashCode(callSuper = true)
public abstract class Created extends RedditObject implements CreatedInterface {

    @JsonProperty("created")
    private DateTime created;

    @JsonProperty("created_utc")
    private DateTime createdUtc;

    protected void setCreated(Long seconds) {
        if (seconds != null) {
            this.created = new DateTime(seconds * 1000, DateTimeZone.UTC);
        }
    }

    protected void setCreatedUtc(Long seconds) {
        if (seconds != null) {
            this.createdUtc = new DateTime(seconds * 1000, DateTimeZone.UTC);
        }
    }

    @Override
    @JsonSerialize(using = DateTimeLongSerializer.class)
    public DateTime getCreated() {
        return created;
    }

    @Override
    @JsonSerialize(using = DateTimeLongSerializer.class)
    public DateTime getCreatedUtc() {
        return createdUtc;
    }

}
