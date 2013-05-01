package com.reddit4j.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.node.ObjectNode;

import com.reddit4j.models.Account;
import com.reddit4j.models.AuthenticationResults;
import com.reddit4j.models.Comment;
import com.reddit4j.models.Link;
import com.reddit4j.models.Message;
import com.reddit4j.models.More;
import com.reddit4j.models.RedditObject;
import com.reddit4j.models.Subreddit;

public class RedditObjectDeserializer extends StdDeserializer<RedditObject> {

    private Map<String, Class<? extends RedditObject>> registry = new HashMap<String, Class<? extends RedditObject>>() {
        private static final long serialVersionUID = 1L;

        {
            put("children", More.class);
            put("link_id", Comment.class);
            put("domain", Link.class);
            put("public_description", Subreddit.class);
            put("subject", Message.class);
            put("is_mod", Account.class);
            put("cookie", AuthenticationResults.class);
        }
    };

    protected RedditObjectDeserializer() {
        super(RedditObject.class);
    }

    @Override
    public RedditObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        Class<? extends RedditObject> redditObjectClass = null;
        Iterator<Entry<String, JsonNode>> elementsIterator = root.getFields();
        while (elementsIterator.hasNext()) {
            Entry<String, JsonNode> element = elementsIterator.next();
            String name = element.getKey();
            if (registry.containsKey(name)) {
                redditObjectClass = registry.get(name);
                break;
            }
        }
        if (redditObjectClass == null) {
            return null;
        }
        return mapper.readValue(root, redditObjectClass);
    }
}
