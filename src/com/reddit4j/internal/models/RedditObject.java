/**
 * 
 */
package com.reddit4j.internal.models;

import java.io.IOException;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.reddit4j.internal.json.RedditObjectMapper;

@Data
public abstract class RedditObject {
    private String id;
    private String name;

    public String toJson() {
        RedditObjectMapper mapper = RedditObjectMapper.getInstance();

        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return StringUtils.EMPTY;
        }
    }
}
