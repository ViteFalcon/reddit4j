package com.reddit4j.internal.json;

import lombok.Getter;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

import com.reddit4j.internal.models.RedditObject;

public class RedditObjectMapper extends org.codehaus.jackson.map.ObjectMapper {

    @Getter(lazy = true)
    private static final RedditObjectMapper instance = new RedditObjectMapper();

    private RedditObjectMapper() {
        super();
        registerModule(new SimpleModule("RedditObjectDeserializerModule", new Version(1, 0, 0, null)) {
            {
                addDeserializer(RedditObject.class, new RedditObjectDeserializer());
            }
        });
        configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        getDeserializationConfig().addHandler(new RedditDeserializationProblemHandler());
    }
}
