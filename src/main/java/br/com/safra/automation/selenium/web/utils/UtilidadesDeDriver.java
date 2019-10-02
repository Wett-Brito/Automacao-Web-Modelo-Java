package br.com.safra.automation.selenium.web.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.safra.automation.selenium.config.LocalDriverManager;
import br.com.safra.automation.selenium.domain.Tempo;

public abstract class UtilidadesDeDriver implements IUtilidadesDeDriver
{
	private WebDriver driver;
	private WebDriverWait wait;
	
	protected UtilidadesDeDriver( WebDriver driver, WebDriverWait wait )
	{
		this.driver = driver;
		this.wait = wait;
	}
	
	protected UtilidadesDeDriver()
	{
		driver = LocalDriverManager.getDriver();
		wait = new WebDriverWait( getDriver(), Tempo._60.qtde() ) ;
	}
	
	/**Retorna o WebDriver desta thread.*/
	public WebDriver getDriver()
	{
		return driver;
	}
	
	/**Retorna o WebDriverWait desta thread.*/
	public WebDriverWait getWait()
	{
		return wait;
	}
}
