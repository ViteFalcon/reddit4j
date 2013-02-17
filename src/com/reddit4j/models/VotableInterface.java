/**
 * 
 */
package com.reddit4j.models;

/**
 * @author Samuel Karp
 * 
 */
public interface VotableInterface {
	/**
	 * 
	 * @return the number of upvotes (includes own)
	 */
	public int getUps();

	/**
	 * 
	 * @return the number of downvotes (includes own)
	 */
	public int getDowns();

	/**
	 * 
	 * @return true if liked by logged-in user, false if disliked, null if not
	 *         voted or not logged in
	 */
	public Boolean getLikes();
}
