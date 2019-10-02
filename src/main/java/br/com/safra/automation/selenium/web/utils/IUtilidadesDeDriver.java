package br.com.safra.automation.selenium.web.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface IUtilidadesDeDriver
{
	WebDriver getDriver();
	
	WebDriverWait getWait();
}
