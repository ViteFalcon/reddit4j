/**
 * 
 */
package com.reddit4j.internal.models;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.reddit4j.internal.json.RedditObjectMapper;

public abstract class RedditObject {
    private String id;
    private String name;

    /**
     * @return the item's identifier, e.g. "8xwlg"
     */
    public String getId() {
        return id;
    }

    /**
     * @return the fullname of comment, e.g. "t1_c3v7f8u"
     */
    public String getName() {
        return name;
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
