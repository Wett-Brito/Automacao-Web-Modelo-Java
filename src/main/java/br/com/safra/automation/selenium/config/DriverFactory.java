package br.com.safra.automation.selenium.config;

import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory
{
	private static final Logger LOG = LogManager.getLogger(DriverFactory.class);
	
	public WebDriver createDriver( ) throws MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		return DriverType.CHROME.getWebDriverObject( capabilities );
		
	}
}
