package com.reddit4j.internal.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class VotableCreated extends Created implements VotableInterface {

    private int ups;

    private int downs;

    private Boolean likes;
}
