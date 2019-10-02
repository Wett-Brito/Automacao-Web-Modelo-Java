package br.com.safra.automation.selenium.web.utils;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ElementoUtil extends UtilidadesDeDriver{


	/**Busca um elemento por localizador By.*/
	public WebElement buscaElemento( By by )
	{
		return getDriver().findElement( by );
	}
	
	/**Busca uma lista de elementos por localizador By.*/
	public List<WebElement> buscaElementos( By by )
	{
		return getDriver().findElements( by );
	}
	
	/**<p>element.getAttribute(atributo)</p>
	 * 
	 * Retorna o valor do atributo de um elemento, por exemplo,
	 * id, name, text, className, readOnly. (Atributos booleanos
	 * retornam "true" ou null)*/
	public String obtemAtributo(By by, String atributo)
	{
		return obtemAtributo( buscaElemento(by), atributo );
	}
	
	/**<p>element.getAttribute(atributo)</p>
	 * 
	 * Retorna o valor do atributo de um elemento, por exemplo,
	 * id, name, text, className, readOnly. (Atributos booleanos
	 * retornam "true" ou null)*/
	public String obtemAtributo(WebElement element, String atributo)
	{
		return element.getAttribute(atributo);
	}
	public void tentaClicarPorXpath() {
		
		while(true){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Coloque o Xpath");
			String Xpath = sc.next();
			
			if (Xpath.equals(""))
				break;
			
			try {
				buscaElemento(By.xpath(Xpath)).click();
				
			} catch (Exception e){
				System.out.println("Não deu certo!" + e.getMessage());
			}
			
		}
	}
	
	public void tentaEscreverPorXpath(String keys)
	{
		while (true)
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("Coloque o Xpath");
			String Xpath = sc.next();
			
			if (Xpath.equals(""))
				break;
			
			try {
				buscaElemento(By.xpath(Xpath)).sendKeys(keys);
				
			} catch (Exception e){
				System.out.println("Não deu certo!" + e.getMessage());
			}
		}
	}
	
	public void FocarElemento(By by) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("window.focus();");
		jse.executeScript("document.getElementById('sexo').focus();");
	}



}
