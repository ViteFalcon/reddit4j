package com.reddit4j.models;

public class AuthenticationResults extends RedditObject {

    private String modhash;
    private String cookie;

    public String getModhash() {
        return modhash;
    }

    public String getCookie() {
        return cookie;
    }

}
