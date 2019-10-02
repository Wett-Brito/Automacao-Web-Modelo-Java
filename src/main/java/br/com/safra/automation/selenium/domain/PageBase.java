package br.com.safra.automation.selenium.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.safra.automation.selenium.config.LocalDriverManager;

public class PageBase
{
	private static final Logger LOG = LogManager.getLogger(PageBase.class);
	private WebDriver driver;
	
	protected PageBase() {}
	
	public static final <T extends PageBase> T getInstance( Class<T> pageClass )
	{
		T newInstance = null;
		try {
			newInstance = (T) pageClass.newInstance();
			PageFactory.initElements( LocalDriverManager.getDriver(), newInstance );
		}
		catch ( InstantiationException | IllegalAccessException x )
		{
			LOG.error( String.format("Erro ao tentar instanciar '%s' ...", pageClass.getCanonicalName()), x );
		}
		return newInstance;
	}
	
	protected WebDriver getDriver()
	{
		return driver;
	}
}
