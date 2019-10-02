package br.com.safra.automation.selenium.exceptions;

public class AutomationException extends RuntimeException
{
	private static final long serialVersionUID = -2609213458716033666L;

	public AutomationException( String message, Throwable cause )
	{
		super(message, cause);
	}

	public AutomationException( String message )
	{
		super(message);
	}

	public AutomationException( Throwable cause )
	{
		super(cause);
	}
}
