package com.reddit4j.models;

import com.reddit4j.internal.models.AuthenticationResults;

public class AuthenticationCredentials {

    private final String username;
    private final String password;
    private AuthenticationResults authResults;

    public AuthenticationCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return modhash if authenticated, null otherwise
     */
    public String getModhash() {
        return authResults != null ? authResults.getModhash() : null;
    }

    /**
     * @return cookie if authenticated, null otherwise
     */
    public String getCookie() {
        return authResults != null ? authResults.getCookie() : null;
    }

    public boolean isAuthenticated() {
        return authResults != null;
    }

    public void setAuthenticationResults(AuthenticationResults authenticationResults) {
        this.authResults = authenticationResults;
    }
}
