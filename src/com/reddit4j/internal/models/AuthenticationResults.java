package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationResults extends RedditObject {

    private String modhash;

    private String cookie;

}
