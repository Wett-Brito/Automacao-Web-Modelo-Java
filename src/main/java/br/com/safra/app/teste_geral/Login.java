package br.com.safra.app.teste_geral;

import br.com.safra.automation.selenium.domain.StepsBase;
import br.com.safra.automation.selenium.web.utils.ClicaUtil;
import br.com.safra.automation.selenium.web.utils.EscritaLeituraUtil;
import br.com.safra.automation.selenium.web.utils.EsperaUtil;
import br.com.safra.automation.selenium.web.utils.ScreenshotUtil;
import cucumber.api.java.pt.E;

public class Login extends StepsBase {

	private LoginPage loginPage = new LoginPage();
	private ClicaUtil clicaPage = new ClicaUtil();
	private EscritaLeituraUtil escritaLeituraUtil = new EscritaLeituraUtil();
	private EsperaUtil esperaUtil = new EsperaUtil();
	private ScreenshotUtil screenshotUtil = new ScreenshotUtil();
	// login

	@E("^Preencho o 'usuario'$")
	public void preenchoOUsuario() throws Throwable {	
		
		escritaLeituraUtil.escreve(loginPage.getCampoUsuario(), "usuario");
	}

	@E("^Preencho a 'conta'$")
	public void preenchAConta() throws Throwable {
		escritaLeituraUtil.escreve(loginPage.getCampoSenha(), "senha");
		screenshotUtil.capturaTela(Massa.getId(), getClass().getSimpleName());
	}

	@E("^Clico no botao entrar$")
	public void clicoNoBotaoEntra() throws Throwable {
		clicaPage.clica(loginPage.getBtnEntrar());
		esperaUtil.espera(2000);
		esperaUtil.esperaAteElementoSumir(loginPage.getBackGroundWait());
		
	}
}