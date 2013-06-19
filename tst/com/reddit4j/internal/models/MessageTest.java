package com.reddit4j.internal.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.reddit4j.internal.json.RedditObjectMapper;
import com.reddit4j.internal.models.Message;
import com.reddit4j.internal.models.RedditObject;
import com.reddit4j.internal.models.RedditThing;

public class MessageTest {
    String messageJson = "{\"kind\": \"t4\",\"data\": {\"body\": \"Thanks!\","
            + "\"was_comment\": false,\"first_message\": 5678,\"name\": \"t4_abcd\","
            + "\"first_message_name\": \"t4_def\",\"created\": 1355838818.0,\"dest\": \"recipient\","
            + "\"author\": \"author\",\"created_utc\": 1355835218.0,"
            + "\"body_html\": \"&lt;!-- SC_OFF --&gt;&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Thanks!&lt;/p&gt;"
            + "\\n&lt;/div&gt;&lt;!-- SC_ON --&gt;\",\"subreddit\": null,\"parent_id\": \"t4_jkl\","
            + "\"context\": \"\",\"replies\": \"\",\"new\": false,\"id\": \"abc\",\"subject\": \"re: Hello!\"}}";

    String bodyHtml = "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;Thanks!&lt;/p&gt;\n&lt;/div&gt;&lt;!-- SC_ON --&gt;";

    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    @Test
    public void testMessageFields() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(messageJson, RedditThing.class);
        assertEquals(Message.class, thing.getData().getClass());
        Message message = (Message) thing.getData();
        assertEquals("Thanks!", message.getBody());
        assertFalse(message.wasComment());
        assertEquals(5678, message.getFirstMessage());
        assertEquals("t4_abcd", message.getName());
        assertEquals("t4_def", message.getFirstMessageName());
        assertEquals(new DateTime(2012, 12, 18, 13, 53, 38, DateTimeZone.UTC), message.getCreated());
        assertEquals(new DateTime(2012, 12, 18, 12, 53, 38, DateTimeZone.UTC), message.getCreatedUtc());
        assertEquals("recipient", message.getDest());
        assertEquals("author", message.getAuthor());
        assertEquals(bodyHtml, message.getBodyHtml());
        assertNull(message.getSubreddit());
        assertEquals("t4_jkl", message.getParentId());
        assertEquals(StringUtils.EMPTY, message.getContext());
        assertEquals(StringUtils.EMPTY, message.getReplies());
        assertFalse(message.isNew());
        assertEquals("abc", message.getId());
        assertEquals("re: Hello!", message.getSubject());
    }

    @Test
    public void testMessageSerialization() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(messageJson, RedditThing.class);
        Message message = (Message) thing.getData();
        String mapperMessageJson = message.toString();
        RedditObject mappedObject = mapper.readValue(mapperMessageJson, RedditObject.class);
        assertEquals(Message.class, mappedObject.getClass());
        Message mappedMessage = (Message) mappedObject;
        assertEquals(message.toString(), mappedMessage.toString());
    }
}
