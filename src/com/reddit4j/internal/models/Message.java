package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;

@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends Created {

    private String author;

    private String dest;

    private String body;

    @JsonProperty("body_html")
    private String bodyHtml;

    private String context;

    @JsonProperty("parent_id")
    private String parentId;

    private String replies;

    private String subject;

    private String subreddit;

    @JsonProperty("was_comment")
    private boolean wasComment;

    @JsonProperty("first_message")
    private int firstMessage;

    @JsonProperty("first_message_name")
    private String firstMessageName;

    @JsonProperty("new")
    private boolean isNew;
}
