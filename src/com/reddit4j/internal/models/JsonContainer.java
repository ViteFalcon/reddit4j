package com.reddit4j.internal.models;

public class JsonContainer {
    /*
     * I heard you liked JSON so I put JSON inside your JSON so you can
     * deserialize while you deserialize.
     */
    private RedditThing json;

    /**
     * Don't ask me why reddit likes to contain the result inside {"json":{}}
     * 
     * @return
     */
    public RedditThing getJson() {
        return json;
    }

    public void setJson(RedditThing thing) {
        json = thing;
    }
}
