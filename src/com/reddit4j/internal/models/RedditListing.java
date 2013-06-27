package com.reddit4j.internal.models;

import java.util.List;

import lombok.Data;

@Data
public class RedditListing {
    private String before;
    private String after;
    private String modhash;
    private List<RedditThing> data;

    /*
     * Note: Getters were left manually specified, as the Javadoc is actually
     * useful here.
     */

    /**
     * @return The fullname of the listing that follows before this page. null
     *         if there is no previous page.
     */
    public String getBefore() {
        return before;
    }

    /**
     * @return The fullname of the listing that follows after this page. null if
     *         there is no next page.
     */
    public String getAfter() {
        return after;
    }

    /**
     * @return This modhash is not the same modhash provided upon login. You do
     *         not need to update your user's modhash everytime you get a new
     *         modhash. You can reuse the modhash given upon login.
     */
    public String getModhash() {
        return modhash;
    }

    /**
     * @return A list of things that this Listing wraps.
     */
    public List<RedditThing> getData() {
        return data;
    }

}
