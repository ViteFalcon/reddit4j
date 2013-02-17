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
	public void testEmptyJson() throws JsonParseException,
			JsonMappingException, IOException {
		String json = "{}";
		RedditThing listing = mapper.readValue(json, RedditThing.class);
		assertNull(listing.getId());
		assertNull(listing.getName());
		assertNull(listing.getData());
		assertNull(listing.getKind());
	}

	@Test
	public void testNullListing() throws JsonParseException,
			JsonMappingException, IOException {
		String json = "{\"id\":\"ID\",\"name\":\"NAME\",\"kind\":\"KIND\"}";
		RedditThing thing = mapper.readValue(json, RedditThing.class);
		assertNull(thing.getData());
		assertEquals("ID", thing.getId());
		assertEquals("NAME", thing.getName());
		assertEquals("KIND", thing.getKind());
	}

	@Test
	public void testMore() throws JsonParseException, JsonMappingException,
			IOException {
		String json = "{\"id\":\"ID\",\"name\":\"NAME\",\"kind\":\"KIND\",\"data\":{\"children\":[\"test\",\"test2\"]}}";
		RedditThing thing = mapper.readValue(json, RedditThing.class);
		assertEquals("ID", thing.getId());
		assertEquals("NAME", thing.getName());
		assertEquals("KIND", thing.getKind());
		More more = (More) thing.getData();
		assertEquals("test", more.getChildren().get(0));
		assertEquals(2, more.getChildren().size());
	}

	@Test
	public void testAccount() throws JsonParseException, JsonMappingException,
			IOException {
		String json = "{\"id\":\"ID\",\"name\":\"NAME\",\"kind\":\"KIND\",\"data\":{\"is_mod\":\"false\"}}";
		RedditThing thing = mapper.readValue(json, RedditThing.class);
		assertEquals("ID", thing.getId());
		assertEquals("NAME", thing.getName());
		assertEquals("KIND", thing.getKind());
		Account account = (Account) thing.getData();
		assertEquals(false, account.isMod());
	}
}