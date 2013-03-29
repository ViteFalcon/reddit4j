package com.reddit4j.utils;

import org.apache.commons.httpclient.NameValuePair;
import static org.junit.Assert.*;
import org.junit.Test;

import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.SortType;

public class HttpClientUtilsTest {

    private static final String REDDIT_API = "reddit API";

    @Test
    public void testSearchQuery_success_basic() {
        SearchQuery query = new SearchQuery(REDDIT_API);
        NameValuePair[] expectedPairs = new NameValuePair[] { new NameValuePair("q", REDDIT_API) };
        NameValuePair[] actualPairs = HttpClientUtils.buildSearchParameters(query);
        assertArrayEquals(expectedPairs, actualPairs);
    }

    @Test
    public void testSearchQuery_success_sortedAndSrRestricted() {
        SearchQuery query = new SearchQuery(REDDIT_API);
        query.setRestrictSubreddit(true);
        query.setSort(SortType.New);
        NameValuePair[] expectedPairs = new NameValuePair[] { new NameValuePair("q", REDDIT_API),
            new NameValuePair("restrict_sr", "true"), new NameValuePair("sort", "New") };
        NameValuePair[] actualPairs = HttpClientUtils.buildSearchParameters(query);
        assertArrayEquals(expectedPairs, actualPairs);
    }

}
