package com.reddit4j.internal.models;

import lombok.Getter;

public class AuthenticationResults extends RedditObject {

    @Getter
    private String modhash;

    @Getter
    private String cookie;

}
