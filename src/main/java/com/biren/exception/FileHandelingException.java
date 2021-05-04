package com.biren.exception;

public class FileHandelingException extends Exception {

	public FileHandelingException() {
	}

	public FileHandelingException(String message) {
		super(message);
	}

	public FileHandelingException(Throwable cause) {
		super(cause);
	}

	public FileHandelingException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileHandelingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
