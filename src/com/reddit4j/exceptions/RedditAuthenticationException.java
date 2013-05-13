package com.reddit4j.exceptions;

import java.util.List;

public class RedditAuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 7808052086050309635L;

    private List<List<String>> errors;

    public RedditAuthenticationException(String error) {
        super(error);
    }

    public RedditAuthenticationException(List<List<String>> errors) {
        super((errors != null && !errors.isEmpty() && !errors.get(0).isEmpty()) ? errors.get(0).get(0) : null);
        this.errors = errors;
    }

    public List<List<String>> getErrors() {
        return errors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<String> errorlist : errors) {
            for (String error : errorlist) {
                sb.append(error);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
