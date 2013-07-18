package com.reddit4j.models;

import lombok.Data;

import org.joda.time.DateTime;

@Data
public class Message {

    public Message(com.reddit4j.internal.models.Message rm) {
        author = rm.getAuthor();
        body = rm.getBody();
        firstMessageName = rm.getFirstMessageName();
        id = rm.getId();
        name = rm.getName();
        parentId = rm.getParentId();
        recipient = rm.getDest();
        renderedBody = rm.getBodyHtml();
        subject = rm.getSubject();
        subreddit = rm.getSubreddit();
        timestamp = rm.getCreatedUtc();
        comment = rm.isWasComment();
        unread = rm.isNew();
    }

    private final String author;
    private final String body;
    private final String firstMessageName;
    private final String id;
    private final String name;
    private final String parentId;
    private final String recipient;
    private final String renderedBody;
    private final String subject;
    private final String subreddit;
    private final DateTime timestamp;
    private final boolean comment;
    private final boolean unread;

}
