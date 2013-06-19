package com.reddit4j.internal.models;

import org.codehaus.jackson.annotate.JsonCreator;

public enum DistinguishedStatus {
    MODERATOR, ADMIN, SPECIAL;

    @JsonCreator
    public static DistinguishedStatus fromJson(String text) {
        return valueOf(text.toUpperCase());
    }
}
