/**
 * 
 */
package com.reddit4j.models;

/**
 * @author Samuel Karp
 * 
 */
public class RedditThing {

    private String kind;
    private RedditObject data;

    /**
     * @return an identifier denoting the object's type (e.g. "Listing", "more",
     *         "t1", "t2")
     */
    public String getKind() {
        return kind;
    }

    public RedditObject getData() {
        return data;
    }

}
