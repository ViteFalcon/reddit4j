package com.reddit4j.clients;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.reddit4j.internal.clients.RedditClient;
import com.reddit4j.models.AuthenticationCredentials;

public class ClientFactory {

    private final RedditClient client;

    @Inject
    public ClientFactory(RedditClient client) {
        this.client = client;
    }

    /**
     * Create a new User object. If the credentials passed are already
     * authenticated (contain appropriate cookie and modhash), no call is made.
     * Otherwise, this constructed object attempts to authenticate using the
     * provided username and password.
     * 
     * @param credentials
     *            AuthenticationCredentials object which is optionally already
     *            authenticated. Not null.
     */
    public User newUser(@NotNull AuthenticationCredentials credentials) {
        return new User(client, credentials);
    }
}
