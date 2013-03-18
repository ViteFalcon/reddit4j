package com.reddit4j.json;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reddit4j.models.Account;
import com.reddit4j.models.Comment;
import com.reddit4j.models.Link;
import com.reddit4j.models.Message;
import com.reddit4j.models.More;
import com.reddit4j.models.Subreddit;

public class RedditObjectDeserializerTest {

    @Mock
    private JsonParser mockJsonParser;

    @Mock
    private DeserializationContext mockDeserializationContext;

    @Mock
    private ObjectMapper mockObjectMapper;

    @Mock
    private ObjectNode mockObjectNode;

    private RedditObjectDeserializer deserializer;

    @Before
    public void setup() throws JsonProcessingException, IOException {
        MockitoAnnotations.initMocks(this);
        when(mockJsonParser.getCodec()).thenReturn(mockObjectMapper);
        when(mockObjectMapper.readTree(mockJsonParser)).thenReturn(mockObjectNode);

        deserializer = new RedditObjectDeserializer();
    }

    @Test
    public void testNoFields() throws JsonProcessingException, IOException {
        Map<String, JsonNode> fields = new HashMap<String, JsonNode>();
        when(mockObjectNode.getFields()).thenReturn(fields.entrySet().iterator());
        assertNull(deserializer.deserialize(mockJsonParser, mockDeserializationContext));
    }

    @Test
    public void testMore() throws JsonProcessingException, IOException {
        setOneField("children");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, More.class);
    }

    @Test
    public void testComment() throws JsonProcessingException, IOException {
        setOneField("link_id");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Comment.class);
    }

    @Test
    public void testLink() throws JsonProcessingException, IOException {
        setOneField("domain");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Link.class);
    }

    @Test
    public void testSubreddit() throws JsonProcessingException, IOException {
        setOneField("public_description");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Subreddit.class);
    }

    @Test
    public void testMessage() throws JsonProcessingException, IOException {
        setOneField("subject");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Message.class);
    }

    @Test
    public void testAccount() throws JsonProcessingException, IOException {
        setOneField("is_mod");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Account.class);
    }

    private void setOneField(final String field) {
        @SuppressWarnings("serial")
        Map<String, JsonNode> fields = new HashMap<String, JsonNode>() {
            {
                put(field, null);
            }
        };
        when(mockObjectNode.getFields()).thenReturn(fields.entrySet().iterator());
    }
}
