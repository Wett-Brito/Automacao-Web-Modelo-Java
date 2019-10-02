package br.com.safra.automation.selenium.exceptions;

public class DataSearchException extends Exception {

	private static final long serialVersionUID = 2678916134913757421L;

	public DataSearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataSearchException(String message) {
		super(message);
	}

	public DataSearchException(Throwable cause) {
		super(cause);
	}
}
