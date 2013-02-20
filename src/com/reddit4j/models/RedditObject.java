/**
 * 
 */
package com.reddit4j.models;

/**
 * @author Samuel Karp
 * 
 */
public abstract class RedditObject {
    private String id;
    private String name;

    /**
     * @return the item's identifier, e.g. "8xwlg"
     */
    public String getId() {
        return id;
    }

    /**
     * @return the fullname of comment, e.g. "t1_c3v7f8u"
     */
    public String getName() {
        return name;
    }
}
