package com.reddit4j.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class MailboxPage {

    public MailboxPage(final com.reddit4j.internal.models.More l) {
        previousId = l.getBefore();
        lastId = l.getAfter();
        messages = new ArrayList<Message>();
        for (com.reddit4j.internal.models.RedditThing th : l.getChildren()) {
            Message m = new Message((com.reddit4j.internal.models.Message) th.getData());
            messages.add(m);
        }
    }

    private final String previousId;
    private final String lastId;
    private final List<Message> messages;

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}
