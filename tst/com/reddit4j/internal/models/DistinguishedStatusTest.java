package com.reddit4j.internal.models;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.reddit4j.internal.models.DistinguishedStatus;

public class DistinguishedStatusTest {

    @Test
    public void testGoodCases() {
        assertEquals(DistinguishedStatus.ADMIN, DistinguishedStatus.fromJson("admin"));
        assertEquals(DistinguishedStatus.MODERATOR, DistinguishedStatus.fromJson("moderator"));
        assertEquals(DistinguishedStatus.SPECIAL, DistinguishedStatus.fromJson("special"));
    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        assertEquals(null, DistinguishedStatus.fromJson(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlank() {
        assertEquals(null, DistinguishedStatus.fromJson(StringUtils.EMPTY));
    }
}
