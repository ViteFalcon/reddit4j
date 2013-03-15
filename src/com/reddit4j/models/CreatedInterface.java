package com.reddit4j.models;

import org.joda.time.DateTime;

public interface CreatedInterface {

    /**
     * 
     * @return the time of creation in local timezone
     */
    public DateTime getCreated();

    /**
     * 
     * @return the time of creation in UTC
     */
    public DateTime getCreatedUtc();
}
