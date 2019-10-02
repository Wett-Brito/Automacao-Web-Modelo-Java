package br.com.safra.app.teste_geral;

import br.com.safra.automation.selenium.web.utils.ScreenshotUtil;
import br.com.safra.automation.selenium.web.utils.UtilidadesDeAcesso;
import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;

public class BrowserBase {

	private UtilidadesDeAcesso acessoUtil = new UtilidadesDeAcesso();
	private BrowserBasePage browserBasePage = new BrowserBasePage();
	private ScreenshotUtil screenshotUtil = new ScreenshotUtil();	
	
	@Dado("^Acesso o site do CRL$")
	public void acessoOSiteDoCRL() throws Throwable
	{
		acessoUtil.acessaLink(browserBasePage.getLink());
		screenshotUtil.capturaTela(Massa.getId(), getClass().getSimpleName());
	}
	
	@Dado("^Acesso o site \"(.*)\"$")
	public void acesso_o_site (String url)
	{
		System.out.println(url);
		try
		{
			acessoUtil.acessaLink(url);
		}
		catch (PendingException ex)
		{
			System.out.println(ex.getStackTrace());
		}
	}

}
