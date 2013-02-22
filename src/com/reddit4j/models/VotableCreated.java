package com.reddit4j.models;


public abstract class VotableCreated extends Created implements VotableInterface {

    private int ups;

    private int downs;

    private Boolean likes;

    @Override
    public int getUps() {
        return ups;
    }

    @Override
    public int getDowns() {
        return downs;
    }

    @Override
    public Boolean getLikes() {
        return likes;
    }

}
