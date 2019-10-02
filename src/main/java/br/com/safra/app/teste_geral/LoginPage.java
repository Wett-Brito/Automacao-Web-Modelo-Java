package br.com.safra.app.teste_geral;

import org.openqa.selenium.By;

import br.com.safra.automation.selenium.domain.PageBase;

public class LoginPage extends PageBase{
		
	// Mapeamento
	private By campoUsuario = By.xpath("//*[@id=\"usuario\"]");
	private By campoSenha = By.xpath("//*[@id=\"senha\"]");
	private By btnEntrar = By.xpath("//button");
	private By backGroundWait = By.xpath("//div[@class=\"loader-backdrop\"]");

	// Metodos Getters e Setters

	public By getCampoUsuario() {
		return campoUsuario;
	}
	public By getCampoSenha() {
		return campoSenha;
	}
	public By getBtnEntrar() {
		return btnEntrar;
	}
	public By getBackGroundWait() {
		return backGroundWait;
	}
}