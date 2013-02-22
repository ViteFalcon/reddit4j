package com.reddit4j.clients;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.joda.time.DateTime;

// TODO rename
public class RedditJavaClient {
	// TODO give these better names
	protected static final int REQUEST_LIMIT_PER_PERIOD = 30;
	protected static final int REQUEST_LIMIT_TIME_PERIOD_MS = 60 * 1000;
	private static final int TIMER_INTERVAL_MS = 2 * 1000;
	private static final int TIMER_DELAY_MS = 0;

	private LinkedList<DateTime> sentRequestTimestamps = new LinkedList<DateTime>();
	private HttpClient httpClient;
	private Timer timer;

	public RedditJavaClient() {
		// default dummy/empty constructor
		HostConfiguration h = new HostConfiguration();

		// TODO set User-Agent string
		h.setHost("reddit.com");
		httpClient = new HttpClient();
		httpClient.setHostConfiguration(h);
	}

	public RedditJavaClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	private int tryRequest(HttpMethod method) {

		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(method);
			// new BasicHttpResponse();
			// httpClient.
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = -1;
		}
		sentRequestTimestamps.addLast(DateTime.now());
		method.releaseConnection();
		return statusCode;
	}

	/*
	 * On each tick (2 seconds apart), drain each request from the request queue
	 * that is at least a minute (REQUEST_LIMIT_TIME_PERIOD_MS) old.
	 */
	private synchronized void tick() {
		while (sentRequestTimestamps.size() > 0) {
			DateTime oldest = sentRequestTimestamps.getFirst();
			if (oldest == null
					|| DateTime.now().compareTo(
							oldest.plusMillis(REQUEST_LIMIT_TIME_PERIOD_MS)) > 0) {
				// evict request from history
				sentRequestTimestamps.removeFirst();
			} else {
				break;
			}
		}
	}
	
	//All of the methods below here are Reddit's exposed APIs. Within this library they should be accessed through layers of abstraction
	
	// TODO when ready, refactor this to return POJO instead of JSON string
	protected String getSubredditInfo(String subreddit) {
		// TODO make factory for Http methods
		// TODO add headers/footers to method
		HttpMethod method = new GetMethod(String.format("/r/%s/about.json",
				subreddit));
		tryRequest(method);
		try {
			return method.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	protected void clearSessions() {
		//post /api/clear_sessions
	}
	
	protected void deleteUser() {
		//post /api/delete_user
	}
	
	protected void login(String username, String password, boolean persistSession) {
		//post /api/login
		//passwd = password
		//user = username
		//rem = persistSession
	}
	
	protected void getInfoAboutMe() {
		//GET /api/me.json
	}
	
	protected void register() {
		//POST /api/register
	}
	
	protected void updateAccount() {
		//POST /api/update
	}
	
	protected void getOAuthIdentity() {
		//GET /api/v1/me
	}
	
	protected void addDeveloper() {
		//POST /api/adddeveloper
	}
	
	protected void deleteApp() {
		//POST /api/deleteapp
	}

	protected void removeDeveloper() {
		//POST /api/removedeveloper
	}
	
	protected void revokeApp() {
		//POST /api/revokeapp
	}
	
	protected void setAppIcon() {
		//POST /api/setappicon
	}
	
	protected void updateApp() {
		//POST /api/updateapp
	}
	
	//flair
	protected void clearFlairTemplates() {
		//POST /api/clearflairtemplates
	}
	
	protected void deleteFlair() {
		//POST /api/deleteflair
	}
	
	protected void deleteFlairTemplate() {
		//POST /api/deleteflairtemplate
	}
	
	protected void flair() {
		//POST /api/flair
	}
	
	protected void flairConfig() {
		//POST /api/flairconfig
	}
	
	protected void flairCsv() {
		//POST /api/flaircsv
	}
	
	protected void flairList() {
		//GET /api/flairlist
	}
	
	protected void flairTemplate() {
		//POST /api/flairtemplate
	}
	
	protected void selectFlair() {
		//POST /api/selectflair
	}
	
	protected void setFlairEnabled(boolean enabled) {
		//I think we should asbtract mod hashes from the user, or maybe create some other sort of user-client association?
		//POST /api/setflairenabled
	}
	
	//links and comments
	
	protected void postComment() {
		//POST /api/comment
	}
	
	protected void deleteComment() {
		//POST /api/del
	}
	
	protected void editUserText() {
		//POST /api/editusertext
	}
	
	protected void hide() {
		//POST /api/hide
	}
	
	protected void info() {
		//GET /api/info
	}
	
	protected void markNsfw() {
		//POST /api/marknsfw
	}
	
	protected void moreChildren() {
		//POST /api/morechildren
	}
	
	protected void report() {
		//POST /api/report
	}
	
	protected void save() {
		//POST /api/save
	}
	
	protected void submit() {
		//POST /api/submit
	}
	
	protected void unhide() {
		//POST /api/unhide
	}
	
	protected void unmarkNsfw() {
		//POST /api/unmarknsfw
	}
	
	protected void unSave() {
		//POST /api/unsave
	}
	
	protected void vote() {
		//POST /api/vote
	}
	
	//listings
	protected void getArticle() {
		//GET /comments/article.json
	}
	
	protected void getHot() {
		//GET /hot.json
	}
	
	protected void getListing() {
		//GET /listing.json
	}
	
	protected void getNew() {
		//GET /new.json
	}
	
	protected void getRandom() {
		//GET /random.json
	}
	
	protected void sort() {
		//GET /sort.json
		//TODO incomplete
	}
	
	//private messages
	protected void block() {
		//POST /api/block
	}
	
	protected void compose() {
		//POST /api/compose
	}
	
	protected void readMessage() {
		//POST /api/read_message
	}
	
	protected void unreadMessage() {
		//POST /api/unread_message
	}
	
	protected void getMessage() {
		//GET /message/inbox.json
		//GET /message/unread.json
		//GET /message/sent.json
	}
	
	//misc
	protected void newCaptcha() {
		//POST /api/new_captcha
	}
	
	//moderation
	
	protected void approve() {
		//POST /api/approve
	}
	
	protected void distinguish() {
		//POST /api/distinguish
	}
	
	protected void ignoreReports() {
		//POST /api/ignore_reports
	}
	
	protected void leaveContributor() {
		//POST /api/leavecontributor
	}
	
	protected void leaveModerator() {
		//POST /api/leavemoderator
	}
	
	protected void remove() {
		//POST /api/remove
	}
	
	protected void unignoreReports() {
		//POST /api/unignore_reports
	}
	
	protected void getModerationLog() {
		//GET /moderationlog
	}
	
	protected void getStylesheet() {
		//GET /stylesheet
	}
	
	//search
	protected void search() {
		//GET /search.json
	}
	
	//subreddits
	protected void acceptModeratorInvite() {
		//POST /api/accept_moderator_invite
	}
	
	protected void deleteSubredditHeader() {
		//POST /api/delete_sr_header
	}
	
	protected void deleteSubredditImage() {
		//POST /api/delete_sr_img
	}
	
	protected void siteAdmin() {
		//POST /api/site_admin
	}
	
	protected void subredditStylesheet() {
		//POST /api/subreddit_stylesheet
	}
	
	protected void getSubredditsByTopic() {
		//GET /api/subreddits_by_topic.json
	}
	
	protected void subscribe() {
		//POST /api/subscribe
	}
	
	protected void uploadSubredditImage() {
		//POST /api/upload_sr_img
	}
	
	protected void getMySubredditsWhere() {
		//GET /reddits/mine/subscriber.json
		//GET /reddits/mine/contributor.json
		//GET /reddits/mine/moderator.json
	}
	
	protected void searchSubreddits() {
		//GET /reddits/search.json	
	}
	
	protected void getSubredditsWhere() {
		//GET /reddits/popular.json
		//GET /reddits/new.json
		//GET /reddits/banned.json
	}
	
	//users 
	protected void friend() {
		//POST /api/friend
	}
	
	protected void setPermissions() {
		//POST /api/setpermissions
	}
	
	protected void unfriend() {
		//POST /api/unfriend
	}
	
	protected void isUsernameAvailable() {
		//GET /api/username_available.json
	}
	
	protected void getUserInfo() {
		//GET /user/{username}/about.json
		//GET /user/{username}/overview.json
		//GET /user/{username}/submitted.json
		//GET /user/{username}/comments.json
		//GET /user/{username}/liked.json
		//GET /user/{username}/disliked.json
		//GET /user/{username}/hidden.json
		//GET /user/{username}/saved.json
	}
	
	//wiki
	protected void allowEditor() {
		//POST /api/wiki/alloweditor/:act
	}
	
	protected void edit() {
		//POST /api/wiki/edit
	}
	
	protected void hideWiki() {
		//POST /api/wiki/hide
	}
	
	protected void revert() {
		//POST /api/wiki/revert
	}
}
