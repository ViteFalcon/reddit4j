package com.reddit4j.models;

import java.util.Date;

public class Link extends VotableCreated {

    private String author;
    private String authorFlairCssClass;
    private String authorFlairText;
    private boolean clicked;
    private String domain;
    private boolean hidden;
    private boolean isSelf;
    private String linkFlairCssClass;
    private String linkFlairText;
    private Object media; // ?????
    private Object mediaEmbed; // ????
    private int numComments;
    private boolean over18;
    private String permalink;
    private boolean saved;
    private int score;
    private String selftext;
    private String selftextHtml;
    private String subreddit;
    private String subredditId;
    private String thumbnail;
    private String title;
    private String url;
    private Date edited; // TODO: change to DateTime
    private DistinguishedStatus distinguished;

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
    public Date getEdited() {
        return edited;
    }

    public DistinguishedStatus getDistinguished() {
        return distinguished;
    }

}
