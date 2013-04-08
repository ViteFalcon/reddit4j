package com.reddit4j.clients;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
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

    public static final String DEFAULT_REGULAR_ENDPOINT = "http://reddit.com";
    public static final String DEFAULT_SECURE_ENDPOINT = "https://reddit.com";

    private final ThrottledHttpClient httpClient;
    private final RedditObjectMapper redditObjectMapper = RedditObjectMapper.getInstance();

    private final Logger logger = LoggerFactory.getLogger(RedditClient.class);

    public RedditClient() {
        // TODO set User-Agent string
        httpClient = new ThrottledHttpClient();
    }

    public RedditClient(ThrottledHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private RedditThing getRedditThing(String uri, HttpParams queryParams) throws JsonParseException,
            JsonMappingException, ClientProtocolException, IOException {
        return redditObjectMapper.readValue(get(uri, queryParams), RedditThing.class);
    }

    private String get(String uri, HttpParams queryParams) throws ClientProtocolException, IOException {
        return httpClient.get(DEFAULT_REGULAR_ENDPOINT + uri, queryParams);
    }

    private RedditThing post(String uri, HttpParams queryParams) throws ClientProtocolException, IOException {
        return post(uri, false, queryParams);
    }

    private RedditThing post(String uri, boolean secure, HttpParams queryParams) throws ClientProtocolException,
            IOException {
        String response = null;
        response = httpClient.post((secure ? DEFAULT_SECURE_ENDPOINT : DEFAULT_REGULAR_ENDPOINT) + uri, queryParams);
        if (response == null) {
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

    protected void clearSessions(final String currentPassword, final String destinationUrl, final String modhash)
            throws HttpException, IOException {
        @SuppressWarnings("serial")
        HttpParams params = new BasicHttpParams() {

            {
                setParameter("curpass", currentPassword);
                setParameter("uh", modhash);
            }
        };
        post("/api/clear_sessions", params);
    }

    protected void deleteUser() {
        // post /api/delete_user
    }

    protected RedditThing getInfoAboutMe() throws HttpException, IOException {
        // GET /api/me.json
        try {
            return getRedditThing("/api/me.json", null);
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

    protected void postComment(String rawCommentText, String parentId, String modhash) throws HttpException,
            IOException {
        HttpParams params = RedditClientUtils.buildIdAndModHashParameters(parentId, modhash);
        params.setParameter("text", rawCommentText);
        post("/api/comment", params);
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

    // TODO there are several "do x"/"do the opposite of x" API calls here.
    // Should our public API have one method in these cases that takes in a
    // boolean parameter?
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

    // TODO add note to user about votes, specifically that they must be done by
    // a human
    protected void vote(Vote vote, String redditThingId, String modhash) throws HttpException, IOException {
        // ignoring vh parameter specified in official API
        HttpParams params = RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash);
        params.setIntParameter("dir", vote.getIntValue());
        post("/api/vote", params);
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
        HttpParams params = RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash);
        params.setParameter("spam", Boolean.toString(markAsSpam));

        post("/api/remove", params);
    }

    protected void unignoreReports(String redditThingId, String modhash) throws HttpException, IOException {
        post("/api/unignore_reports", RedditClientUtils.buildIdAndModHashParameters(redditThingId, modhash));
    }

    protected void getModerationLog() {
        // GET /moderationlog
    }

    protected String getStylesheet(String subreddit) throws JsonParseException, JsonMappingException, HttpException,
            IOException {
        return get(String.format("/r/%s/stylesheet", subreddit), null);
    }

    // search

    protected RedditThing search(SearchQuery query) throws JsonParseException, JsonMappingException, HttpException,
            IOException {
        // TODO swallow Json exceptions
        // TODO use /r/subredditname/search.json when limiting search to a
        // subreddit
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

    protected Object getSubredditsByTopic(final String query) throws HttpException, IOException {
        // return
        // TODO API returns an array of hashes with only one value (subreddit
        // name)
        @SuppressWarnings("serial")
        String s = get("/api/subreddits_by_topic.json", new BasicHttpParams() {
            {
                setParameter("query", query);
            }
        });
        return s;
    }

    protected void subscribe(String subreddit, String modhash) {
        @SuppressWarnings("serial")
        HttpParams params = new BasicHttpParams() {
            {
                setParameter("action", "sub");
            }
        };
        // POST /api/subscribe
    }

    protected void unsubscribe(String subreddit, String modhash) {
        @SuppressWarnings("serial")
        HttpParams params = new BasicHttpParams() {
            {
                setParameter("action", "unsub");
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

    protected boolean isUsernameAvailable(final String username) throws HttpException, IOException {
        @SuppressWarnings("serial")
        String response = get("/api/username_available.json", new BasicHttpParams() {
            {
                setParameter("name", username);
            }
        });
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
