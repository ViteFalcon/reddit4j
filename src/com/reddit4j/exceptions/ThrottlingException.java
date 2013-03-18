package com.reddit4j.exceptions;

import org.joda.time.DateTime;

public class ThrottlingException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final DateTime next;

    /**
     * 
     * @param prev
     *            - DateTime representing the last time this occurred
     * @param throttleMs
     *            - how long (in milliseconds) until the operation is safe again
     */
    public ThrottlingException(final DateTime prev, int throttleMs) {
        this.next = prev.plusMillis(throttleMs);
    }

    public DateTime getNextValid() {
        return next;
    }

}
