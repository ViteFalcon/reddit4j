/**
 * 
 */
package com.reddit4j.models;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.reddit4j.json.RedditObjectMapper;

public class RedditThing {

    private String kind;
    private RedditObject data;

    /**
     * @return an identifier denoting the object's type (e.g. "Listing", "more",
     *         "t1", "t2")
     */
    public String getKind() {
        return kind;
    }

    public RedditObject getData() {
        return data;
    }

    @Override
    public String toString() {
        RedditObjectMapper mapper = RedditObjectMapper.getInstance();

        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return StringUtils.EMPTY;
        }
    }

}
