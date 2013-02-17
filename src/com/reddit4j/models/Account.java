package com.reddit4j.models;

import org.joda.time.DateTime;

public class Account extends RedditObject {
	private int commentKarma;
	private DateTime created;
	private DateTime createdUtc;
	private Boolean hasMail;
	private Boolean hasModMail;
	private String id;
	private boolean is_friend;
	private boolean is_gold;
	private boolean is_mod;
	private int link_karma;
	private String modhash;
	private String name;
	private boolean over18;

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
		return createdUtc;
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
	public boolean isIs_friend() {
		return is_friend;
	}

	/**
	 * @return the reddit gold status
	 */
	public boolean isIs_gold() {
		return is_gold;
	}

	/**
	 * @return whether this account moderates any subreddits
	 */
	public boolean isIs_mod() {
		return is_mod;
	}

	/**
	 * @return the user's link karma
	 */
	public int getLink_karma() {
		return link_karma;
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
