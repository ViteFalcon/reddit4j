package com.reddit4j.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ThrottlingException extends RuntimeException {

    private static final long serialVersionUID = -8892161793709566516L;

    private final DateTime nextValid;

    /**
     * 
     * @param prev
     *            - DateTime representing the last time this occurred
     * @param throttleMs
     *            - how long (in milliseconds) until the operation is safe again
     */
    public ThrottlingException(final DateTime prev, int throttleMs) {
        this.nextValid = prev.plusMillis(throttleMs);
    }
}
