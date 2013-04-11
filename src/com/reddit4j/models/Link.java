package com.reddit4j.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.reddit4j.json.DateTimeLongSerializer;

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
     * @return the account name of the poster. null if this is a promotional
     *         link
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the CSS class of the author's flair. subreddit specific
     */
    public String getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    /**
     * @return the text of the author's flair. subreddit specific
     */
    public String getAuthorFlairText() {
        return authorFlairText;
    }

    /**
     * @return probably always returns false
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * @return the domain of this link. Self posts will be self.reddit.com while
     *         other examples include en.wikipedia.org and s3.amazon.com
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @return true if the post is hidden by the logged in user. false if not
     *         logged in or not hidden.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return true if this link is a selfpost
     */
    @JsonProperty("is_self")
    public boolean isSelf() {
        return isSelf;
    }

    /**
     * @return the CSS class of the link's flair.
     */
    public String getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    /**
     * @return the text of the link's flair.
     */
    public String getLinkFlairText() {
        return linkFlairText;
    }

    /**
     * @return the media
     */
    public Object getMedia() {
        return media;
    }

    /**
     * @return the mediaEmbed
     */
    public Object getMediaEmbed() {
        return mediaEmbed;
    }

    /**
     * @return the number of comments that belong to this link. includes removed
     *         comments.
     */
    public int getNumComments() {
        return numComments;
    }

    /**
     * @return true if the post is tagged as NSFW. False if otherwise
     */
    @JsonProperty("over_18")
    public boolean isOver18() {
        return over18;
    }

    /**
     * @return relative URL of the permanent link for this link
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     * @return true if this post is saved by the logged in user
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * @note A submission's score is simply the number of upvotes minus the
     *       number of downvotes. If five users like the submission and three
     *       users don't it will have a score of 2. Please note that the vote
     *       numbers are not "real" numbers, they have been "fuzzed" to prevent
     *       spam bots etc. So taking the above example, if five users upvoted
     *       the submission, and three users downvote it, the upvote/downvote
     *       numbers may say 23 upvotes and 21 downvotes, or 12 upvotes, and 10
     *       downvotes. The points score is correct, but the vote totals are
     *       "fuzzed".
     * 
     * @return the net-score of the link.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return he raw text. this is the unformatted text which includes the raw
     *         markup characters such as ** for bold. <, >, and & are escaped.
     *         Empty if not present.
     */
    public String getSelftext() {
        return selftext;
    }

    /**
     * @note The html string will be escaped. You must unescape to get the raw
     *       html. Null if not present.
     * @return the formatted escaped html text. this is the html formatted
     *         version of the marked up text. Items that are boldened by ** or
     *         *** will now have &lt;em&gt; or *** tags on them. Additionally,
     *         bullets and numbered lists will now be in html list format.
     */
    public String getSelftextHtml() {
        return selftextHtml;
    }

    /**
     * @return the subreddit
     */
    public String getSubreddit() {
        return subreddit;
    }

    /**
     * @return the subredditId
     */
    public String getSubredditId() {
        return subredditId;
    }

    /**
     * @return full url to the thumbnail for this link; "self" if this is a self
     *         post
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return the title of the link. may contain newlines for some reason
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the link of this post. the permalink if this is a self-post
     */
    public String getUrl() {
        return url;
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

    public DistinguishedStatus getDistinguished() {
        return distinguished;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public String getBannedBy() {
        return bannedBy;
    }

    public int getNumReports() {
        return numReports;
    }

}
