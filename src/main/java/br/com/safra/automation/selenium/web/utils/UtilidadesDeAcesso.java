package br.com.safra.automation.selenium.web.utils;

import org.openqa.selenium.By;

public class UtilidadesDeAcesso extends UtilidadesDeDriver{

	public void acessaLink(String link) {
		getDriver().get(link);
	}
	
	public void escreve(By by, String texto) {
		getDriver().findElement(by).sendKeys(texto);
	}
	
	public void clica(By by) {
		getDriver().findElement(by).click();
	}
	
}
