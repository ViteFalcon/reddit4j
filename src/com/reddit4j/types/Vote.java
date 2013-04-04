package com.reddit4j.types;

public enum Vote {
    Up(1),
    Down(-1),
    Clear(0);
    
    private final int value;

    Vote(int value) {
        this.value = value;
    }
    
    public int getIntValue() {
        return value;
    }
}
