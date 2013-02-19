package com.reddit4j.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Account extends RedditObject {
    @JsonProperty("comment_karma")
    private int commentKarma;

    private DateTime created;

    @JsonProperty("created_utc")
    private DateTime createdUtc;

    @JsonProperty("has_mail")
    private Boolean hasMail;

    @JsonProperty("has_mod_mail")
    private Boolean hasModMail;

    private String id;

    @JsonProperty("is_friend")
    private boolean isFriend;

    @JsonProperty("is_gold")
    private boolean isGold;

    @JsonProperty("is_mod")
    private boolean isMod;

    @JsonProperty("link_karma")
    private int linkKarma;

    private String modhash;

    private String name;

    @JsonProperty("over_18")
    private boolean over18;

    public void setCreated(long seconds) {
        this.created = new DateTime(seconds * 1000, DateTimeZone.UTC);
    }

    public void setCreatedUtc(long seconds) {
        this.createdUtc = new DateTime(seconds * 1000, DateTimeZone.UTC);
    }

    /**
     * @return the user's comment karma
     */
    public int getCommentKarma() {
        return commentKarma;
    }

    /**
     * @return the registration date in epoch-seconds, local
     */
    public DateTime getCreated() {
        return created;
    }

    /**
     * @return the registration date in epoch-seconds, UTC
     */
    public DateTime getCreatedUtc() {
        return new DateTime(createdUtc);
    }

    /**
     * @return user has unread mail? null if not your account
     */
    public Boolean hasMail() {
        return hasMail;
    }

    /**
     * @return the user has unread mod mail? null if not your account
     */
    public Boolean hasModMail() {
        return hasModMail;
    }

    /**
     * @return the ID of the account; prepend t2_ to get fullname
     */
    public String getId() {
        return id;
    }

    /**
     * @return whether the logged-in user has this user set as a friend
     */
    public boolean isFriend() {
        return isFriend;
    }

    /**
     * @return the reddit gold status
     */
    public boolean isGold() {
        return isGold;
    }

    /**
     * @return whether this account moderates any subreddits
     */
    public boolean isMod() {
        return isMod;
    }

    /**
     * @return the user's link karma
     */
    public int getLinkKarma() {
        return linkKarma;
    }

    /**
     * @return the current modhash. not present if not your account
     */
    public String getModhash() {
        return modhash;
    }

    /**
     * @return the The username of the account in question. This attribute
     *         overrides the superclass's name attribute. Do not confuse an
     *         account's name which is the account's username with a thing's
     *         name which is the thing's FULLNAME. See API: Glossary for details
     *         on what FULLNAMEs are.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the whether this account is set to be over 18
     */
    public boolean isOver18() {
        return over18;
    }
}
