package com.reddit4j.models;

import java.util.List;

public class More extends RedditObject {
    private List<RedditThing> children;
    private String modhash;
    private String after;
    private String before;

    public List<RedditThing> getChildren() {
        return children;
    }

    public String getModhash() {
        return modhash;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }
}
