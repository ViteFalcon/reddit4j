package com.reddit4j.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.reddit4j.types.SearchQuery;

public class RedditClientUtils {

    public static final String NAME_PARAM = "name";
    public static final String ID_PARAM = "id";
    public static final String UH_PARAM = "uh";
    public static final String CLIENT_ID_PARAM = "client_id";

    public static List<NameValuePair> buildIdAndModHashParameters(final String id, final String modhash) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(UH_PARAM, modhash));
        if (id != null) {
            params.add(new BasicNameValuePair(ID_PARAM, id));
        }
        return params;
    }

    public static List<NameValuePair> buildSearchParameters(SearchQuery query) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("q", query.getQuery()));
        if (query.getRestrictSubreddit() != null) {
            params.add(new BasicNameValuePair("restrict_sr", query.getRestrictSubreddit().toString()));
        }
        if (query.getAfter() != null) {
            params.add(new BasicNameValuePair("after", query.getAfter()));
        }
        if (query.getBefore() != null) {
            params.add(new BasicNameValuePair("before", query.getBefore()));
        }
        if (query.getCount() != null) {
            params.add(new BasicNameValuePair("count", query.getCount().toString()));
        }
        if (query.getLimit() != null) {
            params.add(new BasicNameValuePair("limit", query.getLimit().toString()));
        }
        if (query.getSort() != null) {
            params.add(new BasicNameValuePair("sort", query.getSort().toString()));
        }
        if (query.getSyntax() != null) {
            params.add(new BasicNameValuePair("syntax", query.getSyntax().toString()));
        }
        if (query.getTimePeriod() != null) {
            params.add(new BasicNameValuePair("t", query.getTimePeriod().toString()));
        }
        if (query.getTarget() != null) {
            params.add(new BasicNameValuePair("target", query.getTarget()));
        }
        return params;
    }

    // TODO use entity to send file
    public static List<NameValuePair> buildAppPostParameters(String clientId, String modhash, String username) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(CLIENT_ID_PARAM, clientId));
        params.add(new BasicNameValuePair(UH_PARAM, modhash));
        if (username != null) {
            params.add(new BasicNameValuePair(NAME_PARAM, username));
        }
        return params;
    }
}
