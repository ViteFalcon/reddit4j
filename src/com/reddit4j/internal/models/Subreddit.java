package com.reddit4j.internal.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class Subreddit extends Created {

    @JsonProperty("accounts_active")
    private int accountsActive;

    private String description;

    @JsonProperty("description_html")
    private String desctiptionHtml;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("header_img")
    private String headerImg;

    @JsonProperty("header_title")
    private String headerTitle;

    @JsonProperty("header_size")
    private int[] headerSize;

    private boolean over18;

    @JsonProperty("public_description")
    private String publicDescription;

    private long subscribers;

    private String title;

    private String url;

    /**
     * @return number of users active in last 15 minutes
     */
    public int getAccountsActive() {
        return accountsActive;
    }

    /**
     * @return the sidebar text
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the sidebar text, escaped HTML format
     */
    public String getDesctiptionHtml() {
        return desctiptionHtml;
    }

    /**
     * @return the human name of the subreddit
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the full URL to the header image, or null
     */
    public String getHeaderImg() {
        return headerImg;
    }

    /**
     * @return description of header image shown on hover, or null
     */
    public String getHeaderTitle() {
        return headerTitle;
    }

    /**
     * @return array describing the header size
     */
    public int[] getHeaderSize() {
        return headerSize;
    }

    /**
     * @return is_nsfw?
     */
    public boolean isOver18() {
        return over18;
    }

    /**
     * @return the description shown in subreddit search results?
     */
    public String getPublicDescription() {
        return publicDescription;
    }

    /**
     * @return the number of redditors subscribed to this subreddit
     */
    public long getSubscribers() {
        return subscribers;
    }

    /**
     * @return the title of the main page
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the relative URL of the subreddit. Ex: "/r/pics/"
     */
    public String getUrl() {
        return url;
    }

}
