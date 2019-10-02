package br.com.safra.automation.selenium.exceptions;

public class ScenarioSearchException extends AutomationException
{
	private static final long serialVersionUID = 4420511572912244598L;

	public ScenarioSearchException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ScenarioSearchException(String message)
	{
		super(message);
	}

	public ScenarioSearchException(Throwable cause)
	{
		super(cause);
	}
}
