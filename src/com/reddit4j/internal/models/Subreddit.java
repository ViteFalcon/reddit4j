package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;

@Data
@EqualsAndHashCode(callSuper = true)
public class Subreddit extends Created {

    @JsonProperty("accounts_active")
    private int accountsActive;

    private String description;

    @JsonProperty("description_html")
    private String desctiptionHtml;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("header_img")
    private String headerImg;

    @JsonProperty("header_title")
    private String headerTitle;

    @JsonProperty("header_size")
    private int[] headerSize;

    private boolean over18;

    @JsonProperty("public_description")
    private String publicDescription;

    private long subscribers;

    private String title;

    private String url;
}
