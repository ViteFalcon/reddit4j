package com.reddit4j.utils;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.SortType;

public class RedditClientUtilsTest {

    private static final String TEST_QUERY = "reddit API";

    @Test
    public void testSearchQuery_success_basic() {
        SearchQuery query = new SearchQuery(TEST_QUERY);
        @SuppressWarnings("serial")
        List<NameValuePair> expectedPairs = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("q", TEST_QUERY));
            }
        };
        List<NameValuePair> actualPairs = RedditClientUtils.buildSearchParameters(query);
        assertTrue(EqualsBuilder.reflectionEquals(expectedPairs, actualPairs));
    }

    @Test
    public void testSearchQuery_success_sortedAndSrRestricted() {
        SearchQuery query = new SearchQuery(TEST_QUERY);
        query.setRestrictSubreddit(true);
        query.setSort(SortType.New);
        @SuppressWarnings("serial")
        List<NameValuePair> expectedPairs = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("q", TEST_QUERY));
                add(new BasicNameValuePair("restrict_sr", "true"));
                add(new BasicNameValuePair("sort", "New"));
            }
        };
        List<NameValuePair> actualPairs = RedditClientUtils.buildSearchParameters(query);
        assertTrue(EqualsBuilder.reflectionEquals(expectedPairs, actualPairs));
    }

    @Test
    public void testAppParamBuilder_success_basic() {
        @SuppressWarnings("serial")
        List<NameValuePair> expectedPairs = new ArrayList<NameValuePair>() {
            {
                add(new BasicNameValuePair("client_id", "testId"));
                add(new BasicNameValuePair("uh", "aoeuhtns"));
            }
        };
        List<NameValuePair> actualPairs = RedditClientUtils.buildAppPostParameters("testId", "aoeuhtns", null);
        assertTrue(EqualsBuilder.reflectionEquals(expectedPairs, actualPairs));
    }

}
