package com.reddit4j.internal.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import com.reddit4j.internal.json.RedditObjectMapper;
import com.reddit4j.internal.models.Account;
import com.reddit4j.internal.models.RedditObject;

public class RedditObjectMapperTest {

    private RedditObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = RedditObjectMapper.getInstance();
    }

    @Test
    public void testAccount() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"is_mod\": true}";
        RedditObject object = objectMapper.readValue(json, RedditObject.class);
        assertEquals(Account.class, object.getClass());
    }

    @Test
    public void testAccount_UnknownField() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"is_mod\": true, \"UNKNOWN_FIELD\": true}";
        RedditObject object = objectMapper.readValue(json, RedditObject.class);
        assertEquals(Account.class, object.getClass());
    }

}
