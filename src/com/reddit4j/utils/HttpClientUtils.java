package com.reddit4j.utils;

import org.apache.commons.httpclient.NameValuePair;

public class HttpClientUtils {
    
    public static NameValuePair[] buildIdAndModHashParameters(String id, String modhash) {
       return new NameValuePair[] {
         new NameValuePair("id", id),
         new NameValuePair("uh", modhash)
       };
    }

}
