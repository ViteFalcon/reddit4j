package com.reddit4j.internal.clients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reddit4j.exceptions.Reddit4jException;
import com.reddit4j.exceptions.RedditAuthenticationException;
import com.reddit4j.internal.json.RedditObjectMapper;
import com.reddit4j.internal.models.Account;
import com.reddit4j.internal.models.AuthenticationResults;
import com.reddit4j.internal.models.JsonContainer;
import com.reddit4j.internal.models.More;
import com.reddit4j.internal.models.RedditThing;
import com.reddit4j.internal.models.Subreddit;
import com.reddit4j.internal.utils.RedditClientUtils;
import com.reddit4j.types.MessageFolder;
import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.Vote;

public class RedditClient {

    public static final String DEFAULT_REGULAR_ENDPOINT = "www.reddit.com";
    public static final String DEFAULT_SECURE_ENDPOINT = "ssl.reddit.com";

    private final ThrottledHttpClient httpClient;
    private final RedditObjectMapper redditObjectMapper = RedditObjectMapper.getInstance();

    private final Logger logger = LoggerFactory.getLogger(RedditClient.class);

    public RedditClient(String userAgent) {
        httpClient = new ThrottledHttpClient(userAgent);
    }

    public RedditClient(ThrottledHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private RedditThing getRedditThing(boolean secure, String path, List<NameValuePair> queryParams) throws IOException {
        try {
            return redditObjectMapper.readValue(get(secure, path, queryParams), RedditThing.class);
        } catch (JsonParseException e) {
            logger.error("Could not parse response to {}", path, e);
            throw new Reddit4jException(e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to {} to an object", path, e);
            throw new Reddit4jException(e);
        }
    }

    private String get(boolean secure, String path, List<NameValuePair> queryParams) throws IOException {
        try {
            return httpClient.get(secure, DEFAULT_REGULAR_ENDPOINT, path, queryParams);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException while GET {}", path, e);
            throw new Reddit4jException(e);
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException while constructing URI to path {}", path, e);
            throw new Reddit4jException(e);
        }
    }

    private RedditThing postRedditThing(boolean secure, String path, List<NameValuePair> queryParams)
            throws IOException {
        try {
            return redditObjectMapper.readValue(post(secure, path, queryParams), RedditThing.class);
        } catch (JsonParseException e) {
            logger.error("Could not parse response to {}", path, e);
            throw new Reddit4jException(e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to {} to an object", path, e);
            throw new Reddit4jException(e);
        }
    }

    private JsonContainer postJsonContainer(boolean secure, String path, List<NameValuePair> queryParams,
            HttpContext localContext) {
        try {
            return redditObjectMapper.readValue(post(secure, path, queryParams, localContext), JsonContainer.class);
        } catch (JsonParseException e) {
            logger.error("Could not parse response to {}", path, e);
            throw new Reddit4jException(e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to {} to an object", path, e);
            throw new Reddit4jException(e);
        } catch (IOException e) {
            logger.error("IOException while parsing response to {}", path, e);
            throw new Reddit4jException(e);
        }
    }

    private String post(boolean secure, String path, List<NameValuePair> queryParams) {
        return post(secure, path, queryParams, null);
    }

    private String post(boolean secure, String path, List<NameValuePair> queryParams, HttpContext localContext) {
        String response;
        try {
            response = httpClient.post(secure, secure ? DEFAULT_SECURE_ENDPOINT : DEFAULT_REGULAR_ENDPOINT, path,
                    queryParams, localContext);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException while POST {}", path, e);
            throw new Reddit4jException(e);
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException while constructing URI to path {}", path, e);
            throw new Reddit4jException(e);
        } catch (IOException e) {
            logger.error("IOException while POST {}", path, e);
            throw new Reddit4jException(e);
        }
        return response;
    }

    // All of the methods below here are Reddit's exposed APIs. Within this
    // library they should be accessed through layers of abstraction

    public Subreddit getSubredditInfo(String subreddit) throws IOException {
        return (Subreddit) getRedditThing(false, String.format("/r/%s/about.json", subreddit), null).getData();
    }

    protected void clearSessions(final String currentPassword, final String destinationUrl, final String modhash)
            throws IOException {
        @SuppressWarnings("serial")
        List<NameValuePair> params = new ArrayList<NameValuePair>() {

            {
                add(new BasicNameValuePair("curpass", currentPassword));
                add(new BasicNameValuePair("uh", modhash));
            }
        };
        post(true, "/api/clear_sessions", params);
    }

    protected void deleteUser() {
        // post /api/delete_user
    }

    protected RedditThing getInfoAboutMe() throws IOException {
        // GET /api/me.json
        try {
            return getRedditThing(false, "/api/me.json", null);
        } catch (JsonParseException e) {
            logger.error("Could not parse response", e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to RedditThing object", e);
        }
        return null;
    }

    @SuppressWarnings("serial")
    public AuthenticationResults login(final String username, final String password, HttpContext localContext) {
        JsonContainer container = null;
        container = postJsonContainer(true, "/api/login", new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("user", username));
                add(new BasicNameValuePair("passwd", password));
                // this parameter is undocumented
                add(new BasicNameValuePair("api_type", "json"));
            }
        }, localContext);

        if (container == null || container.getJson() == null) {
            throw new Reddit4jException("reddit returned weird results when attempting to login for user " + username);
        } else if (container.getJson().getErrors() != null && !container.getJson().getErrors().isEmpty()) {
            throw new RedditAuthenticationException(container.getJson().getErrors());
        } else if (container.getJson().getData() == null
                || container.getJson().getData().getClass() != AuthenticationResults.class) {
            throw new Reddit4jException(
                    "reddit returned a data field that does not appear to be an authentication result");
        }
        return (AuthenticationResults) container.getJson().getData();
    }

    protected void register() {
        // POST /api/register
    }

    protected void updateAccount() {
        // POST /api/update
    }

    protected RedditThing getOAuthIdentity() throws IOException {
        try {
            return getRedditThing(true, "/api/v1/me", null);
        } catch (JsonParseException e) {
            logger.error("Could not parse response", e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to RedditThing object", e);
        }
        return null;
    }

    // apps

    protected void addDeveloper(String clientId, String modhash, String user) throws IOException {
        post(true, "/api/adddeveloper", RedditClientUtils.buildAppPostParameters(clientId, modhash, user));
    }

    protected void removeDeveloper(String clientId, String modhash, String user) throws IOException {
        post(true, "/api/removedeveloper", RedditClientUtils.buildAppPostParameters(clientId, modhash, user));
    }

    protected void deleteApp(String clientId, String modhash) throws IOException {
        post(true, "/api/deleteapp", RedditClientUtils.buildAppPostParameters(clientId, modhash, null));
    }

    protected void revokeApp(String clientId, String modhash) throws IOException {
        post(true, "/api/revokeapp", RedditClientUtils.buildAppPostParameters(clientId, modhash, null));
    }

    protected void setAppIcon() {
        // TODO need new method to support file entities in post
        // POST /api/setappicon
    }

    protected void updateApp() {
        // POST /api/updateapp
    }

    // flair
    protected void clearFlairTemplates() {
        // POST /api/clearflairtemplates
    }

    protected void deleteFlair() {
        // POST /api/deleteflair
    }

    protected void deleteFlairTemplate() {
        // POST /api/deleteflairtemplate
    }

    protected void flair() {
        // POST /api/flair
    }

    protected void flairConfig() {
        // POST /api/flairconfig
    }

    protected void flairCsv() {
        // POST /api/flaircsv
    }

    protected void flairList() {
        // GET /api/flairlist
    }

    protected void flairTemplate() {
        // POST /api/flairtemplate
    }

    protected void selectFlair() {
        // POST /api/selectflair
    }

    protected void setFlairEnabled(boolean enabled) {
        // I think we should asbtract mod hashes from the user, or maybe create
        // some other sort of user-client association?
        // POST /api/setflairenabled
    }

    // links and comments
    // TODO: Make this return a real object instead of a String
    // TODO: fix the parameters - parent and api_type
    public String postComment(String parentId, String rawCommentText, String modhash, HttpContext localContext) {
        List<NameValuePair> params = RedditClientUtils.buildIdAndModHashParameters(null, modhash);
        params.add(new BasicNameValuePair("text", rawCommentText));
        params.add(new BasicNameValuePair("parent", parentId));
        params.add(new BasicNameValuePair("api_type", "json"));
        return post(false, "/api/comment", params, localContext);
    }

    protected void deleteComment(String redditThingId, String modhash) throws IOException {
        post(false, "/api/del", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void editUserText() {
        // POST /api/editusertext
    }

    protected void hide(String redditThingId, String modhash) throws IOException {
        post(false, "/api/hide", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unhide(String redditThingId, String modhash) throws IOException {
        post(false, "/api/unhide", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected RedditThing info() {
        // GET /api/info
        return null;
    }

    // TODO there are several "do x"/"do the opposite of x" API calls here.
    // Should our public API have one method in these cases that takes in a
    // boolean parameter?
    protected void markNsfw(String redditThingId, String modhash) throws IOException {
        post(false, "/api/marknsfw", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unmarkNsfw(String redditThingId, String modhash) throws IOException {
        post(false, "/api/unmarknsfw", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void moreChildren() {
        // POST /api/morechildren
    }

    protected void report(String redditThingId, String modhash) throws IOException {
        post(false, "/api/report", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void save(String redditThingId, String modhash) throws IOException {
        post(false, "/api/save", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unSave(String redditThingId, String modhash) throws IOException {
        post(false, "/api/unsave", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));

    }

    protected void submit() {
        // POST /api/submit
    }

    // TODO add note to user about votes, specifically that they must be done by
    // a human
    protected void vote(Vote vote, String redditThingId, String modhash) throws IOException {
        // ignoring vh parameter specified in official API
        List<NameValuePair> params = RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash);
        params.add(new BasicNameValuePair("dir", Integer.toString(vote.getIntValue())));
        post(false, "/api/vote", params);
    }

    // listings
    protected void getArticle() {
        // GET /comments/article.json
    }

    protected void getHot() {
        // GET /hot.json
    }

    protected void getListing() {
        // GET /listing.json
    }

    protected void getNew() {
        // GET /new.json
    }

    protected void getRandom() {
        // GET /random.json
    }

    protected void sort() {
        // GET /sort.json
        // TODO incomplete
    }

    // private messages
    protected void block(String redditThingId, String modhash) throws IOException {
        post(false, "/api/block", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void compose() {
        // POST /api/compose
    }

    protected void readMessage(String redditThingId, String modhash) throws IOException {
        post(false, "/api/read_message", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unreadMessage(String redditThingId, String modhash) throws IOException {
        post(false, "/api/unread_message", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected RedditThing getMessages(MessageFolder folder) throws IOException {
        if (folder == null) {
            folder = MessageFolder.Inbox;
        }
        return getRedditThing(true, String.format("/message/%s.json", folder.getValue()), null);
    }

    // misc
    protected void newCaptcha() throws IOException {
        post(true, "/api/new_captcha", null);
    }

    // moderation

    protected void approve(String redditThingId, String modhash) throws IOException {
        post(false, "/api/approve", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void distinguish() {
        // POST /api/distinguish
    }

    protected void ignoreReports(String redditThingId, String modhash) throws IOException {
        post(false, "/api/ignore_reports", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void leaveContributor(String redditThingId, String modhash) throws IOException {
        post(false, "/api/leavecontributor", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void leaveModerator(String redditThingId, String modhash) throws IOException {
        post(false, "/api/leavemoderator", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void remove(String redditThingId, boolean markAsSpam, String modhash) throws IOException {
        List<NameValuePair> params = RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash);
        params.add(new BasicNameValuePair("spam", Boolean.toString(markAsSpam)));

        post(false, "/api/remove", params);
    }

    protected void unignoreReports(String redditThingId, String modhash) throws IOException {
        post(false, "/api/unignore_reports", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void getModerationLog() {
        // GET /moderationlog
    }

    protected String getStylesheet(String subreddit) throws IOException {
        return get(false, String.format("/r/%s/stylesheet", subreddit), null);
    }

    // search

    protected RedditThing search(SearchQuery query) throws IOException {
        // TODO swallow Json exceptions
        // TODO use /r/subredditname/search.json when limiting search to a
        // subreddit
        return getRedditThing(false, "/search.json", RedditClientUtils.buildSearchParameters(query));
    }

    // subreddits

    protected void acceptModeratorInvite(String modhash) throws IOException {
        post(false, "/api/accept_moderator_invite", RedditClientUtils.buildIdAndModHashParameters(null, modhash));
    }

    protected void deleteSubredditHeader() {
        // POST /api/delete_sr_header
    }

    protected void deleteSubredditImage() {
        // POST /api/delete_sr_img
    }

    protected void siteAdmin() {
        // POST /api/site_admin
    }

    protected void subredditStylesheet() {
        // POST /api/subreddit_stylesheet
    }

    protected Object getSubredditsByTopic(final String query) throws IOException {
        // return
        // TODO API returns an array of hashes with only one value (subreddit
        // name)
        @SuppressWarnings("serial")
        String s = get(false, "/api/subreddits_by_topic.json", new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("query", query));
            }
        });
        return s;
    }

    protected void subscribe(String subreddit, String modhash) {
        @SuppressWarnings("serial")
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("action", "sub"));
            }
        };
        // POST /api/subscribe
    }

    protected void unsubscribe(String subreddit, String modhash) {
        @SuppressWarnings("serial")
        List<NameValuePair> params = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("action", "unsub"));
            }
        };
        // POST /api/subscribe
    }

    protected void uploadSubredditImage() {
        // POST /api/upload_sr_img
    }

    protected void getMySubredditsWhere() {
        // GET /reddits/mine/subscriber.json
        // GET /reddits/mine/contributor.json
        // GET /reddits/mine/moderator.json
    }

    protected void searchSubreddits() {
        // GET /reddits/search.json
    }

    protected void getSubredditsWhere() {
        // GET /reddits/popular.json
        // GET /reddits/new.json
        // GET /reddits/banned.json
    }

    // users
    protected void addFriend() {
        // POST /api/friend
    }

    protected void setPermissions() {
        // POST /api/setpermissions
    }

    protected void removeFriend() {
        // POST /api/unfriend
    }

    /**
     * Check whether a username is available
     * 
     * @param username
     * @return true when a username is not claimed
     * @throws IOException
     */
    public boolean isUsernameAvailable(final String username) throws IOException {
        @SuppressWarnings("serial")
        String response = get(false, "/api/username_available.json", new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("name", username));
            }
        });
        return Boolean.parseBoolean(response);
    }

    /**
     * Get information about a user account
     * 
     * @param username
     * @return Account object
     * @throws IOException
     */
    public Account getUserInfo(String username) throws IOException {
        return (Account) getRedditThing(false, String.format("/user/%s/about.json", username), null).getData();
    }

    /**
     * Get user submissions
     * 
     * TODO: Pagination, logged-in
     * 
     * @param username
     * @return a <code>More</code> object with <code>Link</code> children
     * @throws IOException
     */
    public More getUserSubmissions(String username) throws IOException {
        return (More) getRedditThing(false, String.format("/user/%s/submitted.json", username), null).getData();
    }

    /**
     * Get user overview. The overview includes both <code>Comment</code>s and
     * <code>Link</code>s.
     * 
     * TODO: Pagination, logged-in
     * 
     * @param username
     * @return a <code>More</code> object with children of either
     *         <code>Comment</code> or <code>Link</code>
     * @throws IOException
     */
    public More getUserOverview(String username) throws IOException {
        return (More) getRedditThing(false, String.format("/user/%s/overview.json", username), null).getData();
    }

    /**
     * Get comments from a user.
     * 
     * TODO: Pagination, logged-in
     * 
     * @param username
     * @return a <code>More</code> object with <code>Comment</code> children
     * @throws IOException
     */
    public More getUserComments(String username) throws IOException {
        return (More) getRedditThing(false, String.format("/user/%s/comments.json", username), null).getData();
    }

    // The below calls will work better with an authenticated session
    // getLiked
    // GET /user/{username}/liked.json
    // getDisliked
    // GET /user/{username}/disliked.json
    // getHidden
    // GET /user/{username}/hidden.json
    // getSaved
    // GET /user/{username}/saved.json

    // wiki
    protected void allowEditor() {
        // POST /api/wiki/alloweditor/:act
    }

    protected void editWiki() {
        // POST /api/wiki/edit
    }

    protected void hideWiki() {
        // POST /api/wiki/hide
    }

    protected void revertWiki() {
        // POST /api/wiki/revert
    }
}
