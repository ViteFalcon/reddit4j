package com.reddit4j.utils;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;

import com.reddit4j.types.SearchQuery;
import com.reddit4j.types.SortType;

public class RedditClientUtilsTest {

    private static final String TEST_QUERY = "reddit API";

    @Test
    public void testSearchQuery_success_basic() {
        SearchQuery query = new SearchQuery(TEST_QUERY);
        @SuppressWarnings("serial")
        HttpParams expectedPairs = new BasicHttpParams() {
            {
                setParameter("q", TEST_QUERY);
            }
        };
        HttpParams actualPairs = RedditClientUtils.buildSearchParameters(query);
        assertTrue(EqualsBuilder.reflectionEquals(expectedPairs, actualPairs));
    }

    @Test
    public void testSearchQuery_success_sortedAndSrRestricted() {
        SearchQuery query = new SearchQuery(TEST_QUERY);
        query.setRestrictSubreddit(true);
        query.setSort(SortType.New);
        @SuppressWarnings("serial")
        HttpParams expectedPairs = new BasicHttpParams() {
            {
                setParameter("q", TEST_QUERY);
                setParameter("restrict_sr", "true");
                setParameter("sort", "New");
            }
        };
        HttpParams actualPairs = RedditClientUtils.buildSearchParameters(query);
        assertTrue(EqualsBuilder.reflectionEquals(expectedPairs, actualPairs));
    }

    @Test
    public void testAppParamBuilder_success_basic() {
        @SuppressWarnings("serial")
        HttpParams expectedPairs = new BasicHttpParams() {
            {
                setParameter("client_id", "testId");
                setParameter("uh", "aoeuhtns");
            }
        };
        HttpParams actualPairs = RedditClientUtils.buildAppPostParameters("testId", "aoeuhtns", null);
        assertTrue(EqualsBuilder.reflectionEquals(expectedPairs, actualPairs));
    }

}
