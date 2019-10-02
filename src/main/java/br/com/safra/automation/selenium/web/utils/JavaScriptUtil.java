package br.com.safra.automation.selenium.web.utils;

import org.openqa.selenium.JavascriptExecutor;

public class JavaScriptUtil extends UtilidadesDeDriver{
	
	public void ScrollDown() {
		Scroll(8888888);
	}
	
	public void ScrollUp() {
		Scroll(-8888888);
	}
	
	public void Scroll(int pixels)
	{
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		jse.executeScript("window.scrollBy(0," + pixels + ")");
	}

}
