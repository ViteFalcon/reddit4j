/**
 * 
 */
package com.reddit4j.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author Samuel Karp
 * 
 */
public class Comment extends VotableCreated {

    @JsonProperty("approved_by")
    private String approvedBy;

    private String author;

    @JsonProperty("author_flair_css_class")
    private String authorFlairCssClass;

    @JsonProperty("author_flair_text")
    private String authorFlairText;

    @JsonProperty("banned_by")
    private String bannedBy;

    private String body;

    @JsonProperty("body_html")
    private String bodyHtml;

    private DateTime edited;

    private int gilded;

    @JsonProperty("link_id")
    private String linkId;

    @JsonProperty("num_reports")
    private Integer numReports;

    @JsonProperty("parent_id")
    private String parentId;

    private String subreddit;

    @JsonProperty("subreddit_id")
    private String subredditId;

    private RedditThing replies;

    public void setEdited(String seconds) {
        try {
            edited = new DateTime(Long.parseLong(seconds) * 1000, DateTimeZone.UTC);
        } catch (Exception e) {
            // null or false or some bad value, either way we don't care
            edited = null;
        }
    }

    /**
     * @return the who approved this comment, null if not logged in or not a
     *         moderator
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * @return the account name of the poster
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the CSS class of the author's flair
     */
    public String getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    /**
     * @return the text of the author's flair
     */
    public String getAuthorFlairText() {
        return authorFlairText;
    }

    /**
     * @return who removed this comment, null if not logged in or not a
     *         moderator
     */
    public String getBannedBy() {
        return bannedBy;
    }

    /**
     * @return the raw text (Markdown format)
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the formatted HTML text as displayed on reddit, escaped
     */
    public String getBodyHtml() {
        return bodyHtml;
    }

    /**
     * @return the DateTime in UTC when edited, null if never edited
     */
    public DateTime getEdited() {
        return edited;
    }

    /**
     * @return the number of times this comment received reddit gold
     */
    public int getGilded() {
        return gilded;
    }

    /**
     * @return the ID of the link this comment is in
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * @return how many time this comment has been reported, null if not a
     *         moderator
     */
    public Integer getNumReports() {
        return numReports;
    }

    /**
     * @return the ID of the thing this comment is a reply to (either link or a
     *         comment in it)
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @return the subreddit, excluding the "/r/" prefix
     */
    public String getSubreddit() {
        return subreddit;
    }

    /**
     * @return the ID of the subreddit
     */
    public String getSubredditId() {
        return subredditId;
    }

    public RedditThing getReplies() {
        return replies;
    }
}
