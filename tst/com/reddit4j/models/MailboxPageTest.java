package com.reddit4j.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.reddit4j.internal.models.More;
import com.reddit4j.internal.models.RedditThing;

public class MailboxPageTest {

    @SuppressWarnings("serial")
    @Test
    public void testConstructor() {
        final com.reddit4j.internal.models.Message rMessage1 = new com.reddit4j.internal.models.Message();
        rMessage1.setBody("message 1");
        final com.reddit4j.internal.models.Message rMessage2 = new com.reddit4j.internal.models.Message();
        rMessage2.setBody("message 2");
        More input = new More();
        input.setAfter("after");
        input.setBefore("before");
        input.setChildren(new ArrayList<RedditThing>() {
            {
                add(new RedditThing() {
                    {
                        setData(rMessage1);
                    }
                });
                add(new RedditThing() {
                    {
                        setData(rMessage2);
                    }
                });
            }
        });

        MailboxPage page = new MailboxPage(input);
        assertEquals(input.getAfter(), page.getLastId());
        assertEquals(input.getBefore(), page.getPreviousId());
        assertEquals(2, page.getMessages().size());
        assertEquals(rMessage1.getBody(), page.getMessages().get(0).getBody());
        assertEquals(rMessage2.getBody(), page.getMessages().get(1).getBody());
    }
}
