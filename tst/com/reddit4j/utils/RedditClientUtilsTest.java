package com.reddit4j.utils;

import org.apache.commons.httpclient.NameValuePair;
import static org.junit.Assert.*;
import org.junit.Test;

import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.SortType;

public class RedditClientUtilsTest {

    private static final String TEST_QUERY = "reddit API";

    @Test
    public void testSearchQuery_success_basic() {
        SearchQuery query = new SearchQuery(TEST_QUERY);
        NameValuePair[] expectedPairs = new NameValuePair[] { new NameValuePair("q", TEST_QUERY) };
        NameValuePair[] actualPairs = RedditClientUtils.buildSearchParameters(query);
        assertArrayEquals(expectedPairs, actualPairs);
    }

    @Test
    public void testSearchQuery_success_sortedAndSrRestricted() {
        SearchQuery query = new SearchQuery(TEST_QUERY);
        query.setRestrictSubreddit(true);
        query.setSort(SortType.New);
        NameValuePair[] expectedPairs = new NameValuePair[] { new NameValuePair("q", TEST_QUERY),
            new NameValuePair("restrict_sr", "true"), new NameValuePair("sort", "New") };
        NameValuePair[] actualPairs = RedditClientUtils.buildSearchParameters(query);
        assertArrayEquals(expectedPairs, actualPairs);
    }

    @Test
    public void testAppParamBuilder_success_basic() {
        NameValuePair[] expectedPairs = new NameValuePair[] { new NameValuePair("client_id", "testId"),
            new NameValuePair("uh", "aoeuhtns") };
        NameValuePair[] actualPairs = RedditClientUtils.buildAppPostParameters("testId", "aoeuhtns", null);
        assertArrayEquals(expectedPairs, actualPairs);
    }

}
