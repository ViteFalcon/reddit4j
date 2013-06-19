package com.reddit4j.internal.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.reddit4j.internal.json.RedditObjectMapper;
import com.reddit4j.internal.models.Account;
import com.reddit4j.internal.models.RedditObject;
import com.reddit4j.internal.models.RedditThing;

public class AccountTest {
    private String accountJson = "{\"kind\": \"t2\", \"data\": {\"has_mail\": null, \"name\": \"jedberg\", \"is_fri"
            + "end\": false, \"created\": 1123131600.0, \"created_utc\": 1123128000.0, \"link_karma\": 41646, \"comme"
            + "nt_karma\": 127749, \"over_18\": true, \"is_gold\": true, \"is_mod\": true, \"id\": \"1wnj\", \"has_mo"
            + "d_mail\": null}}";

    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    @Test
    public void testAccoutFields() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(accountJson, RedditThing.class);
        assertEquals(Account.class, thing.getData().getClass());
        Account account = (Account) thing.getData();
        assertNull(account.hasMail());
        assertEquals("jedberg", account.getName());
        assertFalse(account.isFriend());
        assertEquals(new DateTime(2005, 8, 4, 5, 0, DateTimeZone.UTC), account.getCreated());
        assertEquals(new DateTime(2005, 8, 4, 4, 0, DateTimeZone.UTC), account.getCreatedUtc());
        assertEquals(41646, account.getLinkKarma());
        assertEquals(127749, account.getCommentKarma());
        assertTrue(account.isOver18());
        assertTrue(account.isGold());
        assertTrue(account.isMod());
        assertEquals("1wnj", account.getId());
        assertNull(account.hasModMail());
    }

    @Test
    public void testAccountSerialization() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(accountJson, RedditThing.class);
        Account account = (Account) thing.getData();
        String mapperAccountJson = account.toString();
        RedditObject mappedObject = mapper.readValue(mapperAccountJson, RedditObject.class);
        assertEquals(Account.class, mappedObject.getClass());
        Account mappedAccount = (Account) mappedObject;
        assertEquals(account.toString(), mappedAccount.toString());
    }
}
