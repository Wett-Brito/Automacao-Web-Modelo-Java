package br.com.safra.automation.selenium.config;

import org.openqa.selenium.WebDriver;

public class LocalDriverManager
{
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	 
	public static WebDriver getDriver()
	{
		return webDriver.get();
	}
 
	public static void setDriver(WebDriver driver)
	{
		webDriver.set(driver);
	}
}
