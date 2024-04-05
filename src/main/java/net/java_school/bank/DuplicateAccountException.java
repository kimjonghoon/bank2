package net.java_school.bank;

@SuppressWarnings("serial")
public class DuplicateAccountException extends RuntimeException {

	public DuplicateAccountException() {
        super();
    }

    public DuplicateAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAccountException(String message) {
        super(message);
    }

    public DuplicateAccountException(Throwable cause) {
        super(cause);
    }
}