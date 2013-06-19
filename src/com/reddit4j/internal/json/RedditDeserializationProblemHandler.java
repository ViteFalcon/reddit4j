package com.reddit4j.internal.json;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.DeserializationProblemHandler;
import org.codehaus.jackson.map.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedditDeserializationProblemHandler extends DeserializationProblemHandler {

    private final Logger logger = LoggerFactory.getLogger(RedditDeserializationProblemHandler.class);

    @Override
    public boolean handleUnknownProperty(DeserializationContext ctxt, JsonDeserializer<?> deserializer, Object bean,
            String propertyName) throws IOException, JsonProcessingException {
        logger.warn("Unknown property {} while deserializing", propertyName);
        return true;
    }

}
