package com.biren.exception;

public class StaticFileNotFoundException extends Exception {

	public StaticFileNotFoundException() {
	}

	public StaticFileNotFoundException(String message) {
		super(message);
	}

	public StaticFileNotFoundException(Throwable cause) {
		super(cause);
	}

	public StaticFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public StaticFileNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
