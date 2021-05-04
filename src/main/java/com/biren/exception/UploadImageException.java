package com.biren.exception;

public class UploadImageException extends Exception {

	public UploadImageException() {
	}

	public UploadImageException(String message) {
		super(message);
	}

	public UploadImageException(Throwable cause) {
		super(cause);
	}

	public UploadImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public UploadImageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
