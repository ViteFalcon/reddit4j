package com.reddit4j.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.reddit4j.json.RedditObjectMapper;

public class RedditThingTest {
    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    @Test
    public void testEmptyJson() throws JsonParseException, JsonMappingException, IOException {
        String json = "{}";
        RedditThing listing = mapper.readValue(json, RedditThing.class);
        assertNull(listing.getData());
        assertNull(listing.getKind());
    }

    @Test
    public void testNullListing() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"kind\":\"KIND\"}";
        RedditThing thing = mapper.readValue(json, RedditThing.class);
        assertNull(thing.getData());
        assertEquals("KIND", thing.getKind());
    }

    @Test
    public void testMore() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"kind\":\"KIND\",\"data\":{\"children\":[{\"kind\":\"KIND\"},{\"kind\":\"KIND\"}]}}";
        RedditThing thing = mapper.readValue(json, RedditThing.class);
        assertEquals("KIND", thing.getKind());
        More more = (More) thing.getData();
        assertEquals(RedditThing.class, more.getChildren().get(0).getClass());
        assertEquals(2, more.getChildren().size());
    }

    @Test
    public void testAccount() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"kind\":\"KIND\",\"data\":{\"is_mod\":\"false\"}}";
        RedditThing thing = mapper.readValue(json, RedditThing.class);
        assertEquals("KIND", thing.getKind());
        Account account = (Account) thing.getData();
        assertEquals(false, account.isMod());
    }
}
