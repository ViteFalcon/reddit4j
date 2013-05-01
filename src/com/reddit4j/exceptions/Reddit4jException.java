package com.reddit4j.exceptions;

public class Reddit4jException extends RuntimeException {
    private static final long serialVersionUID = 7560106545865351750L;

    public Reddit4jException(Exception e) {
        super(e);
    }

    public Reddit4jException(String message) {
        super(message);
    }

}
