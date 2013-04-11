package com.reddit4j.types;

public enum MessageFolder {
    Inbox("inbox"),
    Unread("unread"),
    Sent("sent");
    
    private final String value;
    
    private MessageFolder(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
