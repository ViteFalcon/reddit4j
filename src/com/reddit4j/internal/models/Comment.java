/**
 * 
 */
package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

@Data
@EqualsAndHashCode(callSuper = true)
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

    @JsonProperty("score_hidden")
    private Boolean scoreHidden;

    private RedditThing replies;

    private DistinguishedStatus distinguished;

    public void setEdited(String seconds) {
        try {
            edited = new DateTime(Long.parseLong(seconds) * 1000, DateTimeZone.UTC);
        } catch (Exception e) {
            // null or false or some bad value, either way we don't care
            edited = null;
        }
    }
}
