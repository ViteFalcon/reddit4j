/**
 * 
 */
package com.reddit4j.models;

import java.util.Date;

/**
 * @author Samuel Karp
 * 
 */
public class Comment extends VotableCreated {

	private String approvedBy;
	private String author;
	private String authorFlairCssClass;
	private String authorFlairText;
	private String bannedBy;
	private String body;
	private String bodyHtml;
	private Date edited; // TODO: change to JodaTime DateTime
	private int gilded;
	private String linkId;
	private String linkTitle;
	private Integer numReports;
	private String parentId;
	private String subreddit;
	private String subredditId;

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
	public Date getEdited() {
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
	 * @return the title of the link this comment is in
	 */
	public String getLinkTitle() {
		return linkTitle;
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
}
