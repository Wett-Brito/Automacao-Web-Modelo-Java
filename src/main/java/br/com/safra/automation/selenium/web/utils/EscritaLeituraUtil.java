package br.com.safra.automation.selenium.web.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.com.safra.automation.selenium.domain.Tempo;

public class EscritaLeituraUtil {

	ElementoUtil elementoUtil = new ElementoUtil();
	EsperaUtil esperaUtil = new EsperaUtil();
	
	/**Pressiona a tecla <TAB> em um elemento.*/
	public void teclarTab(By by)
	{
		teclarTab( elementoUtil.buscaElemento(by) );
	}
	
	/**Pressiona a tecla <TAB> em um elemento.*/
	public void teclarTab(WebElement element)
	{
		element.sendKeys(Keys.TAB);
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	public String le(By by)
	{
		return le( by, Tempo._60.qtde() );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	public String le(By by, int segundosAteTimeout)
	{
		WebElement element = esperaUtil.esperaVisibilidadeDe( by, segundosAteTimeout );
		return leSimples( element );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	public String le(WebElement element)
	{
		return le( element, Tempo._60.qtde() );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento,
	 * após esperar por sua visibilidade.*/
	public String le(WebElement element, int segundosAteTimeout)
	{
		esperaUtil.esperaVisibilidadeDe( element, segundosAteTimeout );
		return leSimples( element );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento.*/
	public String leSimples(By by)
	{
		return leSimples( elementoUtil.buscaElemento(by) );
	}
	
	/**<p>element.getText()</p>
	 * 
	 * Retorna o texto do elemento.*/
	public String leSimples(WebElement element)
	{
		return element.getText();
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento.*/
	public void limpaSimples(By by)
	{
		limpaSimples( elementoUtil.buscaElemento(by) );
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento.*/
	public void limpaSimples(WebElement element)
	{
		element.clear();
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	public void limpa(By by)
	{
		limpa( by, Tempo._60.qtde() );
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	public void limpa(By by, int segundosAteTimeout)
	{
		WebElement element = esperaUtil.esperaAteEstarClicavel(by, segundosAteTimeout);
		limpaSimples(element);
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	public void limpa(WebElement element)
	{
		limpa( element, Tempo._60.qtde() );
	}
	
	/**<p>element.clear()</p>
	 * 
	 * Limpa o texto do elemento,
	 * após esperar que esteja clicável.*/
	public void limpa(WebElement element, int segundosAteTimeout)
	{
		esperaUtil.esperaAteEstarClicavel(element, segundosAteTimeout);
		limpaSimples(element);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento.*/
	public void escreveSimples(By by, String texto)
	{
		escreveSimples( elementoUtil.buscaElemento(by), texto );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento.*/
	public void escreveSimples(WebElement element, String texto)
	{
		element.sendKeys(texto);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	public void escreve(By by, String texto)
	{
		escreve( by, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	public void escreve(By by, String texto, int segundosAteTimeout)
	{
		if (texto.equals(""))
			return;
		WebElement element = esperaUtil.esperaAteEstarClicavel( by, segundosAteTimeout );
		limpaSimples(element);
		escreveSimples(element, texto);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	public void escreve(WebElement element, String texto)
	{
		escreve( element, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * após esperar estar clicável.*/
	public void escreve(WebElement element, String texto, int segundosAteTimeout)
	{
		esperaUtil.esperaAteEstarClicavel(element, segundosAteTimeout);
		limpaSimples(element);
		escreveSimples(element, texto);
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * sem antes limpá-lo, após esperar estar clicável.*/
	public void escreveSemLimpar(By by, String texto)
	{
		escreveSemLimpar( by, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento,
	 * sem antes limpá-lo, após esperar estar clicável.*/
	public void escreveSemLimpar(By by, String texto, int segundosAteTimeout)
	{
		WebElement element = esperaUtil.esperaAteEstarClicavel( by, segundosAteTimeout );
		escreveSimples( element, texto );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento, sem antes limpá-lo,
	 * após esperar estar clicável.*/
	public void escreveSemLimpar(WebElement element, String texto)
	{
		escreveSemLimpar( element, texto, Tempo._60.qtde() );
	}
	
	/**<p>element.sendKeys(texto)</p>
	 * 
	 * Escreve um texto no elemento, sem antes limpá-lo,
	 * após esperar estar clicável.*/
	public void escreveSemLimpar(WebElement element, String texto, int segundosAteTimeout)
	{
		esperaUtil.esperaAteEstarClicavel( element, segundosAteTimeout );
		escreveSimples( element, texto );
	}
	
	public void seleciona(By by, String texto) {
		Select perfil = new Select(elementoUtil.buscaElemento(by));
		perfil.selectByVisibleText(texto);
	}

	public void seleciona(By by, int index) {
		Select perfil = new Select(elementoUtil.buscaElemento(by));
		perfil.selectByIndex(index);
	}
}
