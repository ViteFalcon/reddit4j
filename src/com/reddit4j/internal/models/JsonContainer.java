package com.reddit4j.internal.models;

import lombok.Data;
import lombok.Delegate;

@Data
public class JsonContainer {
    /*
     * I heard you liked JSON so I put JSON inside your JSON so you can
     * deserialize while you deserialize.
     * 
     * RedditThing's methods are delegated to JsonContainer, so they can be used
     * directly.
     */
    @Delegate
    private RedditThing json;
}
