package com.reddit4j.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.reddit4j.internal.models.AuthenticationResults;

@RequiredArgsConstructor
public class AuthenticationCredentials {

    @NonNull
    @Getter
    private final String username;

    @NonNull
    @Getter
    private final String password;

    @Setter
    private AuthenticationResults authenticationResults;

    /**
     * (This is not a @Delegate-generated method as this should not throw
     * NullPointerException)
     * 
     * @return modhash if authenticated, null otherwise
     */
    public String getModhash() {
        return authenticationResults != null ? authenticationResults.getModhash() : null;
    }

    /**
     * (This is not a @Delegate-generated method as this should not throw
     * NullPointerException)
     * 
     * @return cookie if authenticated, null otherwise
     */
    public String getCookie() {
        return authenticationResults != null ? authenticationResults.getCookie() : null;
    }

    public boolean isAuthenticated() {
        return authenticationResults != null;
    }
}
