package com.reddit4j.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessageTest {

    @Test
    public void testConstructor() {
        com.reddit4j.internal.models.Message input = new com.reddit4j.internal.models.Message();
        input.setAuthor("jedberg");
        input.setBody("body");
        input.setBodyHtml("bodyhtml");
        input.setDest("recipient");
        input.setFirstMessageName("fmn");
        input.setId("id");
        input.setName("name");
        input.setParentId("parentid");
        input.setSubject("subject");
        input.setSubreddit("subreddit");
        input.setWasComment(false);
        input.setNew(true);

        Message output = new Message(input);

        assertEquals(input.getAuthor(), output.getAuthor());
        assertEquals(input.getBody(), output.getBody());
        assertEquals(input.getBodyHtml(), output.getRenderedBody());
        assertEquals(input.getDest(), output.getRecipient());
        assertEquals(input.getFirstMessageName(), output.getFirstMessageName());
        assertEquals(input.getId(), output.getId());
        assertEquals(input.getName(), output.getName());
        assertEquals(input.getParentId(), output.getParentId());
        assertEquals(input.getSubject(), output.getSubject());
        assertEquals(input.getSubreddit(), output.getSubreddit());
        assertEquals(input.isWasComment(), output.isComment());
        assertEquals(input.isNew(), output.isUnread());
    }
}
