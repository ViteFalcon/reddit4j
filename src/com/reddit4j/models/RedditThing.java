/**
 * 
 */
package com.reddit4j.models;

/**
 * @author Samuel Karp
 * 
 */
public class RedditThing {

	private String id;
	private String name;
	private String kind;
	private RedditObject data;

	/**
	 * @return the item's identifier, e.g. "8xwlg"
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the fullname of comment, e.g. "t1_c3v7f8u"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return an identifier denoting the object's type (e.g. "Listing", "more",
	 *         "t1", "t2")
	 */
	public String getKind() {
		return kind;
	}

	public RedditObject getData() {
		return data;
	}

}
