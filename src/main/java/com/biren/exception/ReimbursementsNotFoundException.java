package com.biren.exception;

public class ReimbursementsNotFoundException extends Exception {

	public ReimbursementsNotFoundException() {
	}

	public ReimbursementsNotFoundException(String message) {
		super(message);
	}

	public ReimbursementsNotFoundException(Throwable cause) {
		super(cause);
	}

	public ReimbursementsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementsNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
