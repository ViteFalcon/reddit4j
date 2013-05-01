package com.reddit4j.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.reddit4j.json.RedditObjectMapper;

public class AuthenticationResultsTest {
    String authJson = "{\"json\":{\"data\":{\"modhash\":\"modhash value\",\"cookie\":\"cookie value\"}}}";

    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    @Test
    public void testAuthenticationResultsFields() throws JsonParseException, JsonMappingException, IOException {
        JsonContainer container = mapper.readValue(authJson, JsonContainer.class);
        assertNotNull(container.getJson());
        assertEquals(AuthenticationResults.class, container.getJson().getData().getClass());
        AuthenticationResults results = (AuthenticationResults) container.getJson().getData();
        assertEquals("cookie value", results.getCookie());
        assertEquals("modhash value", results.getModhash());
    }

    @Test
    public void testAuthenticationResultsSerialization() throws JsonParseException, JsonMappingException, IOException {
        JsonContainer container = mapper.readValue(authJson, JsonContainer.class);
        RedditThing thing = container.getJson();
        AuthenticationResults auth = (AuthenticationResults) thing.getData();
        String mapperAuthJson = auth.toString();
        RedditObject mappedObject = mapper.readValue(mapperAuthJson, RedditObject.class);
        assertEquals(AuthenticationResults.class, mappedObject.getClass());
        AuthenticationResults mappedAuth = (AuthenticationResults) mappedObject;
        assertEquals(auth.toString(), mappedAuth.toString());
    }
}
