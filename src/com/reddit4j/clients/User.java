package com.reddit4j.clients;

import javax.validation.constraints.NotNull;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.reddit4j.internal.clients.RedditClient;
import com.reddit4j.models.AuthenticationCredentials;

public class User {

    private final RedditClient client;
    private final AuthenticationCredentials credentials;
    private HttpContext httpContext;
    private CookieStore cookieStore;

    /**
     * Create a new User object. If the credentials passed are already
     * authenticated (contain appropriate cookie and modhash), no call is made.
     * Otherwise, this constructor attempts to authenticate using the provided
     * username and password.
     * 
     * @param client
     *            RedditClient - This should be the same singleton used
     *            everywhere else (for best results, have a singleton
     *            ClientFactory and use the newUser factory method)
     * @param credentials
     *            AuthenticationCredentials object which is optionally already
     *            authenticated
     */
    protected User(@NotNull RedditClient client, @NotNull AuthenticationCredentials credentials) {
        this.client = client;
        this.credentials = credentials;
        this.httpContext = new BasicHttpContext();
        this.cookieStore = new BasicCookieStore();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        if (!credentials.isAuthenticated()) {
            credentials.setAuthenticationResults(client.login(credentials.getUsername(), credentials.getPassword(),
                    httpContext));
        }
    }

    // TODO Return a real object here
    public String postComment(String parentId, String commentText) {
        return client.postComment(parentId, commentText, credentials.getModhash(), httpContext);
    }
}
