package com.reddit4j.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.reddit4j.json.RedditObjectMapper;

public class SubredditTest {

    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    private static final String SUBREDDIT_JSON = "{\"kind\": \"t5\", \"data\": {\"header_img\": \"http://f.thumbs.redditmedia.com"
            + "/wrLvwmPUJT5PTxJY.png\", \"header_title\": null, \"description\": \"Anything related to JAVA !\\n\\nJo"
            + "in us on IRC [freenode.net #reddit-java](irc://irc.freenode.net/#reddit-java)\\n\\n**Related Sub-reddi"
            + "ts**:\\n\\n* [Programming](/r/programming)\\n* [Technology](/r/Technology)\\n* [Learn Programming](/r/"
            + "learnprogramming)\\n* [Java Help](/r/javahelp)\\n* [Java TIL](/r/javaTIL)\\n* [Oracle](/r/oracle)\\n\\"
            + "nJVM Languages \\n\\n* [Clojure](/r/clojure)\\n* [Scala](/r/scala)\\n* [Groovy](/r/groovy)\\n* [ColdFu"
            + "sion](/r/coldfusion)\\n\\nWant to practise your coding? \\n\\n* [DailyProgrammer](/r/dailyprogrammer)"
            + "\", \"description_html\": \"&lt;!-- SC_OFF --&gt;&lt;div class=\\\"md\\\"&gt;&lt;p&gt;Anything related"
            + " to JAVA !&lt;/p&gt;\\n\\n&lt;p&gt;Join us on IRC &lt;a href=\\\"irc://irc.freenode.net/#reddit-java\\"
            + "\"&gt;freenode.net #reddit-java&lt;/a&gt;&lt;/p&gt;\\n\\n&lt;p&gt;&lt;strong&gt;Related Sub-reddits&lt"
            + ";/strong&gt;:&lt;/p&gt;\\n\\n&lt;ul&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/programming\\\"&gt;Programming&l"
            + "t;/a&gt;&lt;/li&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/Technology\\\"&gt;Technology&lt;/a&gt;&lt;/li&gt;\\n"
            + "&lt;li&gt;&lt;a href=\\\"/r/learnprogramming\\\"&gt;Learn Programming&lt;/a&gt;&lt;/li&gt;\\n&lt;li&gt"
            + ";&lt;a href=\\\"/r/javahelp\\\"&gt;Java Help&lt;/a&gt;&lt;/li&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/javaTI"
            + "L\\\"&gt;Java TIL&lt;/a&gt;&lt;/li&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/oracle\\\"&gt;Oracle&lt;/a&gt;&lt"
            + ";/li&gt;\\n&lt;/ul&gt;\\n\\n&lt;p&gt;JVM Languages &lt;/p&gt;\\n\\n&lt;ul&gt;\\n&lt;li&gt;&lt;a href="
            + "\\\"/r/clojure\\\"&gt;Clojure&lt;/a&gt;&lt;/li&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/scala\\\"&gt;Scala&l"
            + "t;/a&gt;&lt;/li&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/groovy\\\"&gt;Groovy&lt;/a&gt;&lt;/li&gt;\\n&lt;li&"
            + "gt;&lt;a href=\\\"/r/coldfusion\\\"&gt;ColdFusion&lt;/a&gt;&lt;/li&gt;\\n&lt;/ul&gt;\\n\\n&lt;p&gt;Wa"
            + "nt to practise your coding? &lt;/p&gt;\\n\\n&lt;ul&gt;\\n&lt;li&gt;&lt;a href=\\\"/r/dailyprogrammer"
            + "\\\"&gt;DailyProgrammer&lt;/a&gt;&lt;/li&gt;\\n&lt;/ul&gt;\\n&lt;/div&gt;&lt;!-- SC_ON --&gt;\", \"ti"
            + "tle\": \"java\", \"url\": \"/r/java/\", \"created\": 1205095819.0, \"created_utc\": 1205092219.0, \"p"
            + "ublic_description\": \"\", \"accounts_active\": 26, \"over18\": false, \"header_size\": [118, 52], "
            + "\"subscribers\": 15774, \"display_name\": \"java\", \"id\": \"2qhd7\", \"name\": \"t5_2qhd7\"}}";

    private static final String DESCRIPTION = "Anything related to JAVA !\n\nJoin us on IRC [freenode.net #reddit-java](irc://irc"
            + ".freenode.net/#reddit-java)\n\n**Related Sub-reddits**:\n\n* [Programming](/r/programming)\n* [Techno"
            + "logy](/r/Technology)\n* [Learn Programming](/r/learnprogramming)\n* [Java Help](/r/javahelp)\n* [Java"
            + " TIL](/r/javaTIL)\n* [Oracle](/r/oracle)\n\nJVM Languages \n\n* [Clojure](/r/clojure)\n* [Scala](/r/s"
            + "cala)\n* [Groovy](/r/groovy)\n* [ColdFusion](/r/coldfusion)\n\nWant to practise your coding? \n\n* [D"
            + "ailyProgr" + "ammer](/r/dailyprogrammer)";

    private static final String DESCRIPTION_HTML = "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;Anything r"
            + "elated to JAVA !&lt;/p&gt;\n\n&lt;p&gt;Join us on IRC &lt;a href=\"irc://irc.freenode.net/#reddit-jav"
            + "a\"&gt;freenode.net #reddit-java&lt;/a&gt;&lt;/p&gt;\n\n&lt;p&gt;&lt;strong&gt;Related Sub-reddits&lt"
            + ";/strong&gt;:&lt;/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"/r/programming\"&gt;Programming&lt;/a&g"
            + "t;&lt;/li&gt;\n&lt;li&gt;&lt;a href=\"/r/Technology\"&gt;Technology&lt;/a&gt;&lt;/li&gt;\n&lt;li&gt;&"
            + "lt;a href=\"/r/learnprogramming\"&gt;Learn Programming&lt;/a&gt;&lt;/li&gt;\n&lt;li&gt;&lt;a href=\"/"
            + "r/javahelp\"&gt;Java Help&lt;/a&gt;&lt;/li&gt;\n&lt;li&gt;&lt;a href=\"/r/javaTIL\"&gt;Java TIL&lt;/a"
            + "&gt;&lt;/li&gt;\n&lt;li&gt;&lt;a href=\"/r/oracle\"&gt;Oracle&lt;/a&gt;&lt;/li&gt;\n&lt;/ul&gt;\n\n&l"
            + "t;p&gt;JVM Languages &lt;/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"/r/clojure\"&gt;Clojure&lt;/a&g"
            + "t;&lt;/li&gt;\n&lt;li&gt;&lt;a href=\"/r/scala\"&gt;Scala&lt;/a&gt;&lt;/li&gt;\n&lt;li&gt;&lt;a href="
            + "\"/r/groovy\"&gt;Groovy&lt;/a&gt;&lt;/li&gt;\n&lt;li&gt;&lt;a href=\"/r/coldfusion\"&gt;ColdFusion&lt"
            + ";/a&gt;&lt;/li&gt;\n&lt;/ul&gt;\n\n&lt;p&gt;Want to practise your coding? &lt;/p&gt;\n\n&lt;ul&gt;\n&"
            + "lt;li&gt;&lt;a href=\"/r/dailyprogrammer\"&gt;DailyProgrammer&lt;/a&gt;&lt;/li&gt;\n&lt;/ul&gt;\n&lt;"
            + "/div&gt;&lt;!-- SC_ON --&gt;";

    @Test
    public void testSubredditFields() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(SUBREDDIT_JSON, RedditThing.class);
        assertEquals(Subreddit.class, thing.getData().getClass());
        Subreddit subreddit = (Subreddit) thing.getData();

        assertEquals("http://f.thumbs.redditmedia.com/wrLvwmPUJT5PTxJY.png", subreddit.getHeaderImg());
        assertNull(subreddit.getHeaderTitle());
        assertEquals(118, subreddit.getHeaderSize()[0]);
        assertEquals(52, subreddit.getHeaderSize()[1]);
        assertEquals(DESCRIPTION, subreddit.getDescription());
        assertEquals(DESCRIPTION_HTML, subreddit.getDesctiptionHtml());
        assertEquals("java", subreddit.getTitle());
        assertEquals("/r/java/", subreddit.getUrl());
        assertEquals(StringUtils.EMPTY, subreddit.getPublicDescription());
        assertEquals(26, subreddit.getAccountsActive());
        assertFalse(subreddit.isOver18());
        assertEquals(15774, subreddit.getSubscribers());
        assertEquals("java", subreddit.getDisplayName());
        assertEquals("2qhd7", subreddit.getId());
        assertEquals("t5_2qhd7", subreddit.getName());
        assertEquals(new DateTime(2008, 3, 9, 20, 50, 19, DateTimeZone.UTC), subreddit.getCreated());
        assertEquals(new DateTime(2008, 3, 9, 19, 50, 19, DateTimeZone.UTC), subreddit.getCreatedUtc());
    }
}
