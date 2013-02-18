package com.reddit4j.exceptions;

import org.joda.time.DateTime;

public class ThrottlingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final DateTime next;

	public ThrottlingException(final DateTime next) {
		this.next = next;
	}

	public DateTime getNextValid() {
		return next;
	}

}
