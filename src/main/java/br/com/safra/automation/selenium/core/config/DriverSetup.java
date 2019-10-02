package br.com.safra.automation.selenium.core.config;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverSetup
{
    WebDriver getWebDriverObject(DesiredCapabilities capabilities) throws MalformedURLException;
}