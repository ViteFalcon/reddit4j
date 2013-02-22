package com.reddit4j.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.reddit4j.json.RedditObjectMapper;

public class CommentTest {
    String commentJson = "{\"kind\": \"t1\", "
            + "\"data\": {\"subreddit_id\": \"t5_2qhc9\", \"banned_by\": null, \"link_id\": \"t3_150dw8\", \"likes\""
            + ": null, \"replies\": {\"kind\": \"Listing\", \"data\": {\"modhash\": \"\", \"children\": [{\"kind\": "
            + "\"t1\", \"data\": {\"subreddit_id\": \"t5_2qhc9\", \"banned_by\": null, \"link_id\": \"t3_150dw8\", "
            + "\"likes\": null, \"replies\": \"\", \"id\": \"c7im0td\", \"gilded\": 0, \"author\": \"spladug\", \"pa"
            + "rent_id\": \"t1_c7ih8et\", \"approved_by\": null, \"body\": \"Absolutely, yes please! That'd be fanta"
            + "stic.\", \"edited\": false, \"author_flair_css_class\": null, \"downs\": 0, \"body_html\": \"&lt;div "
            + "class=\\\"md\\\"&gt;&lt;p&gt;Absolutely, yes please! That&amp;#39;d be fantastic.&lt;/p&gt;\\n&lt;/di"
            + "v&gt;\", \"subreddit\": \"changelog\", \"name\": \"t1_c7im0td\", \"created\": 1355859474.0, \"author_"
            + "flair_text\": null, \"created_utc\": 1355855874.0, \"num_reports\": null, \"ups\": 4}}], \"after\": n"
            + "ull, \"before\": null}}, \"id\": \"c7ih8et\", \"gilded\": 0, \"author\": \"umbrae\", \"parent_id\": "
            + "\"t3_150dw8\", \"approved_by\": null, \"body\": \"Hey spladug, would you be interested in a video scr"
            + "eencapture of a blind user using reddit? I've got one from a couple years back when I was considering"
            + " building a better screen reader. Could help you understand where sticky points are.\", \"edited\": f"
            + "alse, \"author_flair_css_class\": null, \"downs\": 0, \"body_html\": \"&lt;div class=\\\"md\\\"&gt;&l"
            + "t;p&gt;Hey spladug, would you be interested in a video screencapture of a blind user using reddit? I&"
            + "amp;#39;ve got one from a couple years back when I was considering building a better screen reader. C"
            + "ould help you understand where sticky points are.&lt;/p&gt;\\n&lt;/div&gt;\", \"subreddit\": \"change"
            + "log\", \"name\": \"t1_c7ih8et\", \"created\": 1355838818.0, \"author_flair_text\": null, \"created_ut"
            + "c\": 1355835218.0, \"num_reports\": null, \"ups\": 7}}";

    String commentText = "Hey spladug, would you be interested in a video screencapture of a blind user using reddit?"
            + " I've got one from a couple years back when I was considering building a better screen reader. Could h"
            + "elp you understand where sticky points are.";

    String commentHtml = "&lt;div class=\"md\"&gt;&lt;p&gt;Hey spladug, would you be interested in a video screen"
            + "capture of a blind user using reddit? I&amp;#39;ve got one from a couple years back when I was consideri"
            + "ng building a better screen reader. Could help you understand where sticky points are.&lt;/p&gt;\n&lt;"
            + "/div&gt;";

    String replyText = "Absolutely, yes please! That'd be fantastic.";

    private ObjectMapper mapper = RedditObjectMapper.getInstance();

    @Test
    public void testCommentNesting() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(commentJson, RedditThing.class);
        assertEquals(Comment.class, thing.getData().getClass());
        Comment comment = (Comment) thing.getData();
        assertNotNull(comment.getReplies());
        assertEquals(More.class, comment.getReplies().getData().getClass());
        More replyList = (More) comment.getReplies().getData();
        assertEquals(Comment.class, replyList.getChildren().get(0).getData().getClass());
    }

    @Test
    public void testCommentFields() throws JsonParseException, JsonMappingException, IOException {
        RedditThing thing = mapper.readValue(commentJson, RedditThing.class);
        Comment comment = (Comment) thing.getData();
        More replyList = (More) comment.getReplies().getData();
        Comment reply = (Comment) replyList.getChildren().get(0).getData();

        assertEquals(commentText, comment.getBody());
        assertEquals(replyText, reply.getBody());

        assertEquals("t5_2qhc9", comment.getSubredditId());
        assertEquals("changelog", comment.getSubreddit());
        assertEquals("t3_150dw8", comment.getParentId());
        assertNull(comment.getNumReports());
        assertEquals("t3_150dw8", comment.getLinkId());
        assertEquals(0, comment.getGilded());
        assertNull(comment.getEdited());
        assertEquals(commentHtml, comment.getBodyHtml());
        assertNull(comment.getBannedBy());
        assertNull(comment.getAuthorFlairText());
        assertNull(comment.getAuthorFlairCssClass());
        assertEquals("umbrae", comment.getAuthor());
        assertNull(comment.getApprovedBy());
    }
}
