package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.reddit4j.internal.json.DateTimeLongSerializer;

@Data
@EqualsAndHashCode(callSuper = true)
public class Link extends VotableCreated {

    private String author;

    @JsonProperty("author_flair_css_class")
    private String authorFlairCssClass;

    @JsonProperty("author_flair_text")
    private String authorFlairText;
    private boolean clicked;
    private String domain;
    private boolean hidden;

    @JsonProperty("is_self")
    private boolean isSelf;

    @JsonProperty("link_flair_css_class")
    private String linkFlairCssClass;

    @JsonProperty("link_flair_text")
    private String linkFlairText;
    private Object media; // ?????

    @JsonProperty("media_embed")
    private Object mediaEmbed; // ????

    @JsonProperty("num_comments")
    private int numComments;

    @JsonProperty("over_18")
    private boolean over18;
    private String permalink;
    private boolean saved;
    private int score;
    private String selftext;

    @JsonProperty("selftext_html")
    private String selftextHtml;
    private String subreddit;

    @JsonProperty("subreddit_id")
    private String subredditId;
    private String thumbnail;
    private String title;
    private String url;
    private DateTime edited;
    private DistinguishedStatus distinguished;

    @JsonProperty("approved_by")
    private String approvedBy;

    @JsonProperty("banned_by")
    private String bannedBy;

    @JsonProperty("num_reports")
    private int numReports;

    /**
     * "edited" field has special properties - it should either be the string
     * "false" or the time of edit in seconds since the unix epoch
     * 
     * @see <a href="https://github.com/reddit/reddit/issues/581">reddit issue
     *      #581</a>
     * @param value
     *            the string "false" or seconds since the unix epoch
     */
    protected void setEdited(String value) {
        try {
            Double seconds = Double.parseDouble(value);
            if (seconds != null) {
                this.edited = new DateTime((long) (seconds * 1000), DateTimeZone.UTC);
            }
        } catch (NumberFormatException e) {
            edited = null;
        }
    }

    /**
     * @return Indicates if link has been edited. Will be the edit timestamp if
     *         the link has been edited and return null otherwise.
     *         https://github.com/reddit/reddit/issues/581
     */
    @JsonSerialize(using = DateTimeLongSerializer.class)
    public DateTime getEdited() {
        return edited;
    }
}
