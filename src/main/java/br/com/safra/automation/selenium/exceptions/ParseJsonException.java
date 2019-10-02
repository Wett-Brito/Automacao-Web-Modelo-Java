package br.com.safra.automation.selenium.exceptions;

/**
 * Exception quando ocorrer erro ao realizar parse do Json
 * 
 * @author regis.rocha
 *
 */
public class ParseJsonException extends AutomationException {

    /**
     * serial version
     */
    private static final long serialVersionUID = 281536643562080339L;

    public ParseJsonException(String message) {
        super(message);
    }

    public ParseJsonException(Throwable cause) {
        super(cause);
    }

    public ParseJsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
