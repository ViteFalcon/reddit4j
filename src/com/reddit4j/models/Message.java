package com.reddit4j.models;

public class Message extends Created {
    private String author;
    private String body;
    private String bodyHtml;
    private String context;
    // Message???
    private String name;
    private boolean newUnread;
    private String parentId;
    private String replies;
    private String subject;
    private String subreddit;
    private boolean wasComment;

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
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
     * @return unread? not sure
     */
    public boolean isNewUnread() {
        return newUnread;
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
    public boolean isWasComment() {
        return wasComment;
    }
}
