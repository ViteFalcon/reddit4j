package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;

@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends Created {
    @JsonProperty("comment_karma")
    private int commentKarma;

    @JsonProperty("has_mail")
    private Boolean hasMail;

    @JsonProperty("has_mod_mail")
    private Boolean hasModMail;

    private String id;

    @JsonProperty("is_friend")
    private boolean isFriend;

    @JsonProperty("is_gold")
    private boolean isGold;

    @JsonProperty("is_mod")
    private boolean isMod;

    @JsonProperty("link_karma")
    private int linkKarma;

    private String modhash;

    private String name;

    @JsonProperty("over_18")
    private boolean over18;
}
