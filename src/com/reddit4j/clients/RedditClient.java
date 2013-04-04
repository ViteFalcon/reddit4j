package com.reddit4j.clients;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reddit4j.json.RedditObjectMapper;
import com.reddit4j.models.RedditThing;
import com.reddit4j.models.Subreddit;
import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.Vote;
import com.reddit4j.utils.RedditClientUtils;

public class RedditClient {

    public static final String DEFAULT_REDDIT_ENDPOINT = "reddit.com";

    private final ThrottledHttpClient httpClient;
    private final RedditObjectMapper redditObjectMapper = RedditObjectMapper.getInstance();

    private final Logger logger = LoggerFactory.getLogger(RedditClient.class);

    public RedditClient() {
        HostConfiguration h = new HostConfiguration();

        // TODO set User-Agent string
        h.setHost(DEFAULT_REDDIT_ENDPOINT);
        httpClient = new ThrottledHttpClient();
        httpClient.setHostConfiguration(h);
    }

    public RedditClient(ThrottledHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private RedditThing getRedditThing(String uri, NameValuePair[] queryParams) throws JsonParseException, JsonMappingException,
            HttpException, IOException {
        return redditObjectMapper.readValue(get(uri, queryParams), RedditThing.class);
    }
    
    private String get(String uri, NameValuePair[] queryParams) throws HttpException, IOException {
        HttpMethod method = null;
        String response = null;
        try {
            method = httpClient.get(uri, queryParams);
            response = method.getResponseBodyAsString();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return response;
    }

    private RedditThing post(String uri, NameValuePair[] requestBody)
        throws HttpException, IOException {
        HttpMethod method = null;
        String response = null;
        try {
            method = httpClient.post(uri, requestBody);
            response = method.getResponseBodyAsString();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        if(response == null) {
            return null;
        }
        return redditObjectMapper.readValue(response, RedditThing.class);
    }
    
    // All of the methods below here are Reddit's exposed APIs. Within this
    // library they should be accessed through layers of abstraction

    public Subreddit getSubredditInfo(String subreddit) throws JsonParseException, JsonMappingException, HttpException,
        IOException {
        return (Subreddit) getRedditThing(String.format("/r/%s/about.json", subreddit), null).getData();
    }

    protected void clearSessions(String currentPassword, String destinationUrl, String modhash) throws HttpException,
        IOException {
        NameValuePair[] requestBody = new NameValuePair[] { new NameValuePair("curpass", currentPassword),
            new NameValuePair("dest", destinationUrl), new NameValuePair("uh", modhash), };
        post("/api/clear_sessions", requestBody);
    }

    protected void deleteUser() {
        // post /api/delete_user
    }

    protected RedditThing getInfoAboutMe() throws HttpException, IOException {
        // GET /api/me.json
        try {
            return getRedditThing("/api/me.json", null);
        } catch(JsonParseException e) {
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

    protected RedditThing getOAuthIdentity() throws HttpException, IOException {
        try {
            return getRedditThing("/api/v1/me", null);
        } catch (JsonParseException e) {
            logger.error("Could not parse response", e);
        } catch (JsonMappingException e) {
            logger.error("Could not map response to RedditThing object", e);
        }
        return null;
    }

    // apps
    
    protected void addDeveloper(String clientId, String modhash, String user) throws HttpException, IOException {
        post("/api/adddeveloper", RedditClientUtils.buildAppPostParameters(clientId, modhash, user));
    }

    protected void removeDeveloper(String clientId, String modhash, String user) throws HttpException, IOException {
        post("/api/removedeveloper", RedditClientUtils.buildAppPostParameters(clientId, modhash, user));
    }

    protected void deleteApp(String clientId, String modhash) throws HttpException, IOException {
        post("/api/deleteapp", RedditClientUtils.buildAppPostParameters(clientId, modhash, null));
    }

    protected void revokeApp(String clientId, String modhash) throws HttpException, IOException {
        post("/api/revokeapp", RedditClientUtils.buildAppPostParameters(clientId, modhash, null));
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

    protected void postComment(String rawCommentText, String parentId, String modhash) throws HttpException, IOException {
        List<NameValuePair> requestBody = new LinkedList<NameValuePair>();
        requestBody.add(new NameValuePair("text", rawCommentText));
        requestBody.addAll(Arrays.asList(RedditClientUtils.buildIdAndModHashParameters(parentId, modhash)));
        post("/api/comment", requestBody.toArray(new NameValuePair[requestBody.size()]));
    }

    protected void deleteComment(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/del", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void editUserText() {
        // POST /api/editusertext
    }

    protected void hide(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/hide", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unhide(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/unhide", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected RedditThing info() {
        // GET /api/info
        return null;
    }

    // TODO there are several "do x"/"do the opposite of x" API calls here. Should our public API have one method in these cases that takes in a boolean parameter?
    protected void markNsfw(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/marknsfw", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unmarkNsfw(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/unmarknsfw", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }
    
    protected void moreChildren() {
        // POST /api/morechildren
    }

    protected void report(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/report", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void save(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/save", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }
    
    protected void unSave(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/unsave", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));

    }
    
    protected void submit() {
        // POST /api/submit
    }

    // TODO add note to user about votes, specifically that they must be done by a human
    protected void vote(Vote vote, String redditThingId, String modhash) throws HttpException, IOException {
        // ignoring vh parameter specified in official API
        
        List<NameValuePair> requestBody = new LinkedList<NameValuePair>();
        requestBody.add(new NameValuePair("dir", Integer.toString(vote.getIntValue())));
        requestBody.addAll(Arrays.asList(RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash)));
        post("/api/vote", requestBody.toArray(new NameValuePair[requestBody.size()]));
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
    protected void block(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/block", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void compose() {
        // POST /api/compose
    }

    protected void readMessage(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/read_message", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void unreadMessage(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/unread_message", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void getMessage() {
        // GET /message/inbox.json
        // GET /message/unread.json
        // GET /message/sent.json
    }

    // misc
    protected void newCaptcha() throws HttpException, IOException {
        post("/api/new_captcha", null);
    }

    // moderation

    protected void approve(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/approve", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void distinguish() {
        // POST /api/distinguish
    }

    protected void ignoreReports(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/ignore_reports", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void leaveContributor(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/leavecontributor", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void leaveModerator(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/leavemoderator", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void remove(String redditThingId, boolean markAsSpam, String modhash) throws HttpException, IOException {
        List<NameValuePair> requestBody = new LinkedList<NameValuePair>();
        requestBody.add(new NameValuePair("spam", Boolean.toString(markAsSpam)));
        requestBody.addAll(Arrays.asList(RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash)));
        post("/api/remove", requestBody.toArray(new NameValuePair[requestBody.size()]));
    }

    protected void unignoreReports(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/unignore_reports", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void getModerationLog() {
        // GET /moderationlog
    }

    protected String getStylesheet(String subreddit) throws JsonParseException, JsonMappingException, HttpException, IOException {
        return get(String.format("/r/%s/stylesheet", subreddit), null);
    }

    // search
    
    protected RedditThing search(SearchQuery query) throws JsonParseException, JsonMappingException, HttpException, IOException {
        // TODO swallow Json exceptions
        // TODO use /r/subredditname/search.json when limiting search to a subreddit
        return getRedditThing("/search.json", RedditClientUtils.buildSearchParameters(query));
    }

    // subreddits
    
    protected void acceptModeratorInvite(String modhash) throws HttpException, IOException {
        post("/api/accept_moderator_invite", RedditClientUtils.buildIdAndModHashParameters(null, modhash));
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

    protected Object getSubredditsByTopic(String query) throws HttpException, IOException {
        //return 
            //TODO API returns an array of hashes with only one value (subreddit name)
        String s = get("/api/subreddits_by_topic.json", new NameValuePair[] { new NameValuePair("query", query) });
        return s;
    }

    protected void subscribe(String subreddit, String modhash) {
        NameValuePair action = new NameValuePair("action", "sub");
        // POST /api/subscribe
    }

    protected void unsubscribe(String subreddit, String modhash) {
        NameValuePair action = new NameValuePair("action", "unsub");
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

    protected boolean isUsernameAvailable(String username) throws HttpException, IOException {
        String response = get("/api/username_available.json", new NameValuePair[] {new NameValuePair("name", username) });
        return Boolean.parseBoolean(response);
    }

    protected void getUserInfo() {
        // GET /user/{username}/about.json
        // GET /user/{username}/overview.json
        // GET /user/{username}/submitted.json
        // GET /user/{username}/comments.json
        // GET /user/{username}/liked.json
        // GET /user/{username}/disliked.json
        // GET /user/{username}/hidden.json
        // GET /user/{username}/saved.json
    }

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
