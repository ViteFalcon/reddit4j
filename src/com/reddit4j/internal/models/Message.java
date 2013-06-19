package com.reddit4j.internal.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class Message extends Created {

    private String author;

    private String dest;

    private String body;

    @JsonProperty("body_html")
    private String bodyHtml;

    private String context;

    private String name;

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

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the recipient
     */
    public String getDest() {
        return dest;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the bodyHtml
     */
    public String getBodyHtml() {
        return bodyHtml;
    }

    /**
     * @return does not seem to return null but an empty string instead.
     */
    public String getContext() {
        return context;
    }

    /**
     * @return ex: "t4_8xwlg"
     */
    public String getName() {
        return name;
    }

    /**
     * @return null if no parent is attached
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @return an empty string if there are no replies.
     */
    public String getReplies() {
        return replies;
    }

    /**
     * @return the subject of message
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return null if not a comment.
     */
    public String getSubreddit() {
        return subreddit;
    }

    /**
     * @return the wasComment
     */
    public boolean wasComment() {
        return wasComment;
    }

    public int getFirstMessage() {
        return firstMessage;
    }

    public String getFirstMessageName() {
        return firstMessageName;
    }

    public boolean isNew() {
        return isNew;
    }
}
