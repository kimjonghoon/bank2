package net.java_school.bank;

@SuppressWarnings("serial")
public class InvalidAccountException extends RuntimeException {

	public InvalidAccountException() {
		super();
	}

	public InvalidAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAccountException(String message) {
		super(message);
	}

	public InvalidAccountException(Throwable cause) {
		super(cause);
	}
}