package com.reddit4j.internal.json;

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

import com.reddit4j.internal.json.RedditObjectDeserializer;
import com.reddit4j.internal.models.Account;
import com.reddit4j.internal.models.AuthenticationResults;
import com.reddit4j.internal.models.Comment;
import com.reddit4j.internal.models.Link;
import com.reddit4j.internal.models.Message;
import com.reddit4j.internal.models.More;
import com.reddit4j.internal.models.Subreddit;

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
        setFields("children");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, More.class);
    }

    @Test
    public void testComment() throws JsonProcessingException, IOException {
        setFields("link_id");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Comment.class);
    }

    @Test
    public void testLink() throws JsonProcessingException, IOException {
        setFields("domain");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Link.class);
    }

    @Test
    public void testSubreddit() throws JsonProcessingException, IOException {
        setFields("public_description");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Subreddit.class);
    }

    @Test
    public void testMessage() throws JsonProcessingException, IOException {
        setFields("subject");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Message.class);
    }

    @Test
    public void testAccount() throws JsonProcessingException, IOException {
        setFields("is_mod");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Account.class);
    }

    @Test
    public void testAccount_UnknownField() throws JsonProcessingException, IOException {
        setFields("is_mod", "UNKNOWN_FIELD");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, Account.class);
    }

    @Test
    public void testAuthenticationResults() throws JsonProcessingException, IOException {
        setFields("cookie");
        deserializer.deserialize(mockJsonParser, mockDeserializationContext);
        verify(mockObjectMapper, times(1)).readValue(mockObjectNode, AuthenticationResults.class);
    }

    private void setFields(final String... fields) {
        @SuppressWarnings("serial")
        Map<String, JsonNode> fieldsMap = new HashMap<String, JsonNode>() {
            {
                for (String field : fields) {
                    put(field, null);
                }
            }
        };
        when(mockObjectNode.getFields()).thenReturn(fieldsMap.entrySet().iterator());
    }
}
