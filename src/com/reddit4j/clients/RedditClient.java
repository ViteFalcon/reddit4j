package com.reddit4j.clients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reddit4j.exceptions.Reddit4jException;
import com.reddit4j.json.RedditObjectMapper;
import com.reddit4j.models.Account;
import com.reddit4j.models.RedditThing;
import com.reddit4j.models.Subreddit;
import com.reddit4j.types.MessageFolder;
import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.Vote;
import com.reddit4j.utils.RedditClientUtils;

public class RedditClient {

    public static final String DEFAULT_ENDPOINT = "reddit.com";

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
            return httpClient.get(secure, DEFAULT_ENDPOINT, path, queryParams);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException while GET {}", path, e);
            throw new Reddit4jException(e);
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException while constructing URI to path {}", path, e);
            throw new Reddit4jException(e);
        }
    }

    private RedditThing post(boolean secure, String path, List<NameValuePair> queryParams) throws IOException {
        String response;
        try {
            response = httpClient.post(secure, DEFAULT_ENDPOINT, path, queryParams);
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException while POST {}", path, e);
            throw new Reddit4jException(e);
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException while constructing URI to path {}", path, e);
            throw new Reddit4jException(e);
        }
        if (response == null) {
            return null;
        }
        try {
            return redditObjectMapper.readValue(response, RedditThing.class);
        } catch (JsonParseException e) {
            logger.error("Could not parse response to {}", path, e);
            throw new Reddit4jException(e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to {} to an object", path, e);
            throw new Reddit4jException(e);
        }
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

    protected void postComment(String rawCommentText, String parentId, String modhash) throws IOException {
        List<NameValuePair> params = RedditClientUtils.buildIdAndModHashParameters(parentId, modhash);
        params.add(new BasicNameValuePair("text", rawCommentText));
        post(false, "/api/comment", params);
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
        if(folder == null) {
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
    protected void friend() {
        // POST /api/friend
    }

    protected void setPermissions() {
        // POST /api/setpermissions
    }

    protected void unfriend() {
        // POST /api/unfriend
    }

    protected boolean isUsernameAvailable(final String username) throws IOException {
        @SuppressWarnings("serial")
        String response = get(false, "/api/username_available.json", new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("name", username));
            }
        });
        return Boolean.parseBoolean(response);
    }

    /**
     * Get user information
     * 
     * @param username
     * @return Account object
     * @throws IOException
     */
    public Account getUserInfo(String username) throws IOException {
        return (Account) getRedditThing(false, String.format("/user/%s/about.json", username), null).getData();
    }

    // getOverview
    // GET /user/{username}/overview.json
    // getSubmissions
    // GET /user/{username}/submitted.json
    // getComments
    // GET /user/{username}/comments.json
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

    protected void edit() {
        // POST /api/wiki/edit
    }

    protected void hideWiki() {
        // POST /api/wiki/hide
    }

    protected void revert() {
        // POST /api/wiki/revert
    }
}
