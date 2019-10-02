package br.com.safra.automation.selenium.exceptions;

public class UserSearchException extends Exception {

	private static final long serialVersionUID = 7328630463616087690L;

	public UserSearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserSearchException(String message) {
		super(message);
	}

	public UserSearchException(Throwable cause) {
		super(cause);
	}
}
