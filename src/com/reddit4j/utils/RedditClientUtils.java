package com.reddit4j.utils;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.reddit4j.types.SearchQuery;

public class RedditClientUtils {

    public static final String NAME_PARAM = "name";
    public static final String ID_PARAM = "id";
    public static final String UH_PARAM = "uh";
    public static final String CLIENT_ID_PARAM = "client_id";

    public static HttpParams buildIdAndModHashParameters(final String id, final String modhash) {
        HttpParams params = new BasicHttpParams();
        params.setParameter(UH_PARAM, modhash);
        if (id != null) {
            params.setParameter(ID_PARAM, id);
        }
        return params;
    }

    public static HttpParams buildSearchParameters(SearchQuery query) {
        HttpParams params = new BasicHttpParams();
        params.setParameter("q", query.getQuery());
        if (query.getRestrictSubreddit() != null) {
            params.setParameter("restrict_sr", query.getRestrictSubreddit().toString());
        }
        if (query.getAfter() != null) {
            params.setParameter("after", query.getAfter());
        }
        if (query.getBefore() != null) {
            params.setParameter("before", query.getBefore());
        }
        if (query.getCount() != null) {
            params.setParameter("count", query.getCount().toString());
        }
        if (query.getLimit() != null) {
            params.setParameter("limit", query.getLimit().toString());
        }
        if (query.getSort() != null) {
            params.setParameter("sort", query.getSort().toString());
        }
        if (query.getSyntax() != null) {
            params.setParameter("syntax", query.getSyntax().toString());
        }
        if (query.getTimePeriod() != null) {
            params.setParameter("t", query.getTimePeriod().toString());
        }
        if (query.getTarget() != null) {
            params.setParameter("target", query.getTarget());
        }
        return params;
    }

    // TODO use entity to send file
    public static HttpParams buildAppPostParameters(String clientId, String modhash, String username) {
        HttpParams params = new BasicHttpParams();
        params.setParameter(CLIENT_ID_PARAM, clientId);
        params.setParameter(UH_PARAM, modhash);
        if (username != null) {
            params.setParameter(NAME_PARAM, username);
        }
        return params;
    }

}
