package com.reddit4j.utils;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import com.reddit4j.types.SearchQuery;

public class HttpClientUtils {

    public static NameValuePair[] buildIdAndModHashParameters(String id, String modhash) {
        return new NameValuePair[] { new NameValuePair("id", id), new NameValuePair("uh", modhash) };
    }

    public static NameValuePair[] buildSearchParameters(SearchQuery query) {
        List<NameValuePair> pairs = new LinkedList<NameValuePair>();
        pairs.add(new NameValuePair("q", query.getQuery()));
        if(query.getRestrictSubreddit() != null) {
            pairs.add(new NameValuePair("restrict_sr", query.getRestrictSubreddit().toString()));
        }
        if(query.getAfter() != null) {
            pairs.add(new NameValuePair("after", query.getAfter()));
        }
        if(query.getBefore() != null) {
            pairs.add(new NameValuePair("before", query.getBefore()));
        }
        if(query.getCount() != null) {
            pairs.add(new NameValuePair("count", query.getCount().toString()));
        }
        if(query.getLimit() != null) {
            pairs.add(new NameValuePair("limit", query.getLimit().toString()));
        }
        if(query.getSort() != null) {
            pairs.add(new NameValuePair("sort", query.getSort().toString()));
        }
        if(query.getSyntax() != null) {
            pairs.add(new NameValuePair("syntax", query.getSyntax().toString()));
        }
        if(query.getTimePeriod() != null) {
            pairs.add(new NameValuePair("t", query.getTimePeriod().toString()));
        }
        if(query.getTarget() != null) {
            pairs.add(new NameValuePair("target", query.getTarget()));
        }
        return pairs.toArray(new NameValuePair[pairs.size()]);
    }

}
