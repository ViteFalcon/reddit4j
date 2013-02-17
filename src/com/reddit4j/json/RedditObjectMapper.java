package com.reddit4j.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import com.reddit4j.models.RedditObject;

public class RedditObjectMapper extends org.codehaus.jackson.map.ObjectMapper {

	private static RedditObjectMapper instance;

	private RedditObjectMapper() {
		super();
		registerModule(new SimpleModule("RedditObjectDeserializerModule",
				new Version(1, 0, 0, null)) {
			{
				addDeserializer(RedditObject.class,
						new RedditObjectDeserializer());
			}
		});
	}

	public static RedditObjectMapper getInstance() {
		if (instance == null) {
			instance = new RedditObjectMapper();
		}
		return instance;
	}
}
