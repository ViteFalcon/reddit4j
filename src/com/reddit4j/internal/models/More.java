package com.reddit4j.internal.models;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class More extends RedditObject {
    private List<RedditThing> children;
    private String modhash;
    private String after;
    private String before;
}
