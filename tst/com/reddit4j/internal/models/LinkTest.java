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
import com.reddit4j.internal.models.DistinguishedStatus;
import com.reddit4j.internal.models.Link;
import com.reddit4j.internal.models.RedditObject;
import com.reddit4j.internal.models.RedditThing;

public class LinkTest {

    String linkJson = "{\"kind\": \"t3\", \"data\": {\"domain\": \"self.changelog\", \"banned_by\": null, \"media_em"
            + "bed\": {}, \"subreddit\": \"changelog\", \"selftext_html\": \"&lt;!-- SC_OFF --&gt;&lt;div class=\\\"md"
            + "\\\"&gt;&lt;p&gt;We&amp;#39;re now slightly more buzzword compliant. You should see no difference in th"
            + "e site except a few extra bytes shaved off the page.&lt;/p&gt;\\n\\n&lt;p&gt;If you run the &lt;a href="
            + "\\\"http://validator.w3.org/\\\"&gt;W3C validator&lt;/a&gt;, you&amp;#39;ll discover a seldom-noticed r"
            + "eddit easter egg...&lt;/p&gt;\\n\\n&lt;p&gt;Pull requests to fix any lingering validation issues gladly"
            + " accepted. ;)&lt;/p&gt;\\n\\n&lt;p&gt;&lt;a href=\\\"https://github.com/reddit/reddit/compare/6ebac049c"
            + "d373d5f45eb82855cfe2c127581ff1f...8b3e0175abbdf479bb2ab69e011f11acc9624457\\\"&gt;see the code on githu"
            + "b&lt;/a&gt;&lt;/p&gt;\\n&lt;/div&gt;&lt;!-- SC_ON --&gt;\", \"selftext\": \"We're now slightly more buz"
            + "zword compliant. You should see no difference in the site except a few extra bytes shaved off the page."
            + "\\n\\nIf you run the [W3C validator](http://validator.w3.org/), you'll discover a seldom-noticed reddit"
            + " easter egg...\\n\\nPull requests to fix any lingering validation issues gladly accepted. ;)\\n\\n[see "
            + "the code on github](https://github.com/reddit/reddit/compare/6ebac049cd373d5f45eb82855cfe2c127581ff1f.."
            + ".8b3e0175abbdf479bb2ab69e011f11acc9624457)\", \"likes\": null, \"link_flair_text\": null, \"id\": \"yro"
            + "5x\", \"clicked\": false, \"title\": \"[reddit change] HTML5 doctype\", \"media\": null, \"score\": 63,"
            + " \"approved_by\": null, \"over_18\": false, \"hidden\": false, \"thumbnail\": \"self\", \"subreddit_id"
            + "\": \"t5_2qhc9\", \"edited\": 1345835127.0, \"link_flair_css_class\": null, \"author_flair_css_class\":"
            + " null, \"downs\": 18, \"saved\": false, \"is_self\": true, \"permalink\": \"/r/changelog/comments/yro5x"
            + "/reddit_change_html5_doctype/\", \"name\": \"t3_yro5x\", \"created\": 1345838498.0, \"url\": \"http://w"
            + "ww.reddit.com/r/changelog/comments/yro5x/reddit_change_html5_doctype/\", \"author_flair_text\": null, "
            + "\"author\": \"chromakode\", \"created_utc\": 1345834898.0, \"ups\": 81, \"num_comments\": 3, \"num_repo"
            + "rts\": null, \"distinguished\": \"admin\"}}";

    String linkSelftext = "We're now slightly more buzzword compliant. You should see no difference in the site exce"
            + "pt a few extra bytes shaved off the page.\n\nIf you run the [W3C validator](http://validator.w3.org/)"
            + ", you'll discover a seldom-noticed reddit easter egg...\n\nPull requests to fix any lingering validat"
            + "ion issues gladly accepted. ;)\n\n[see the code on github](https://github.com/reddit/reddit/compare/6"
            + "ebac049cd373d5f45eb82855cfe2c127581ff1f...8b3e0175abbdf479bb2ab69e011f11acc9624457)";

    String linkSelftextHtml = "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;We&amp;#39;re now slightly "
            + "more buzzword compliant. You should see no difference in the site except a few extra bytes shaved off t"
            + "he page.&lt;/p&gt;\n\n&lt;p&gt;If you run the &lt;a href=\"http://validator.w3.org/\"&gt;W3C vali"
            + "dator&lt;/a&gt;, you&amp;#39;ll discover a seldom-noticed reddit easter egg...&lt;/p&gt;\n\n&lt;p&gt;"
            + "Pull requests to fix any lingering validation issues gladly accepted. ;)&lt;/p&gt;\n\n&lt;p&gt;&lt;a "
            + "href=\"https://github.com/reddit/reddit/compare/6ebac049cd373d5f45eb82855cfe2c127581ff1f...8b3e0175ab"
            + "bdf479bb2ab69e011f11acc9624457\"&gt;see the code on github&lt;/a&gt;&lt;/p&gt;\n&lt;/div&gt;&lt;!-- "
            + "SC_ON --&gt;";

    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    @Test
    public void testLinkFields() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(linkJson, RedditThing.class);
        assertEquals(Link.class, thing.getData().getClass());
        Link link = (Link) thing.getData();
        assertEquals("chromakode", link.getAuthor());
        assertEquals("self.changelog", link.getDomain());
        assertNull(link.getBannedBy());
        assertNull(link.getMedia());
        assertEquals("changelog", link.getSubreddit());
        assertEquals(linkSelftext, link.getSelftext());
        assertEquals(linkSelftextHtml, link.getSelftextHtml());
        assertNull(link.getLikes());
        assertNull(link.getLinkFlairText());
        assertNull(link.getLinkFlairCssClass());
        assertEquals("yro5x", link.getId());
        assertFalse(link.isClicked());
        assertEquals("[reddit change] HTML5 doctype", link.getTitle());
        assertEquals(63, link.getScore());
        assertNull(link.getApprovedBy());
        assertFalse(link.isOver18());
        assertFalse(link.isHidden());
        assertEquals("self", link.getThumbnail());
        assertEquals("t5_2qhc9", link.getSubredditId());
        assertEquals(new DateTime(2012, 8, 24, 19, 5, 27, DateTimeZone.UTC), link.getEdited());
        assertNull(link.getAuthorFlairText());
        assertNull(link.getAuthorFlairCssClass());
        assertEquals(18, link.getDowns());
        assertFalse(link.isSaved());
        assertTrue(link.isSelf());
        assertEquals("/r/changelog/comments/yro5x/reddit_change_html5_doctype/", link.getPermalink());
        assertEquals("t3_yro5x", link.getName());
        assertEquals(new DateTime(2012, 8, 24, 20, 1, 38, DateTimeZone.UTC), link.getCreated());
        assertEquals(new DateTime(2012, 8, 24, 19, 1, 38, DateTimeZone.UTC), link.getCreatedUtc());
        assertEquals("http://www.reddit.com/r/changelog/comments/yro5x/reddit_change_html5_doctype/", link.getUrl());
        assertEquals(81, link.getUps());
        assertEquals(3, link.getNumComments());
        assertEquals(0, link.getNumReports());
        assertEquals(DistinguishedStatus.ADMIN, link.getDistinguished());
    }

    @Test
    public void testLinkSerialization() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(linkJson, RedditThing.class);
        Link link = (Link) thing.getData();
        String mapperLinkJson = link.toString();
        RedditObject mappedObject = mapper.readValue(mapperLinkJson, RedditObject.class);
        assertEquals(Link.class, mappedObject.getClass());
        Link mappedLink = (Link) mappedObject;
        assertEquals(link.toString(), mappedLink.toString());
    }

}
