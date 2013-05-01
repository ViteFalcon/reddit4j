/**
 * 
 */
package com.reddit4j.models;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.reddit4j.json.RedditObjectMapper;

public class RedditThing {

    private String kind;
    private RedditObject data;
    private List<List<String>> errors;

    /**
     * @return an identifier denoting the object's type (e.g. "Listing", "more",
     *         "t1", "t2")
     */
    public String getKind() {
        return kind;
    }

    /**
     * 
     * @return the actual contents of a reddit response (why does reddit like to
     *         encapsulate everything inside this container object?)
     */
    public RedditObject getData() {
        return data;
    }

    /**
     * When reddit returns an error, this field will be non-null and the other
     * fields will be null.
     * 
     * @return (usually) null, otherwise reddit has an error
     */
    public List<List<String>> getErrors() {
        return errors;
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
