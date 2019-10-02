package br.com.safra.automation.selenium.exceptions;

public class ConfigurationException extends AutomationException
{
	private static final long serialVersionUID = 4420511572912244598L;

	public ConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ConfigurationException(String message)
	{
		super(message);
	}

	public ConfigurationException(Throwable cause)
	{
		super(cause);
	}
}
