package com.reddit4j.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class RedditListingTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testEmptyJson() throws JsonParseException, JsonMappingException, IOException {
        String json = "{}";
        RedditListing listing = mapper.readValue(json, RedditListing.class);
        assertNull(listing.getAfter());
        assertNull(listing.getBefore());
        assertNull(listing.getData());
        assertNull(listing.getModhash());
    }

    @Test
    public void testNullListing() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"before\":\"BEFORE\",\"after\":\"AFTER\",\"modhash\":\"MODHASH\"}";
        RedditListing listing = mapper.readValue(json, RedditListing.class);
        assertNull(listing.getData());
        assertEquals(listing.getAfter(), "AFTER");
        assertEquals(listing.getBefore(), "BEFORE");
        assertEquals(listing.getModhash(), "MODHASH");
    }
}
