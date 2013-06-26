package com.reddit4j.internal.json;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.DeserializationProblemHandler;
import org.codehaus.jackson.map.JsonDeserializer;

@Slf4j
public class RedditDeserializationProblemHandler extends DeserializationProblemHandler {

    @Override
    public boolean handleUnknownProperty(DeserializationContext ctxt, JsonDeserializer<?> deserializer, Object bean,
            String propertyName) throws IOException, JsonProcessingException {
        log.warn("Unknown property {} while deserializing", propertyName);
        return true;
    }

}
