package br.com.safra.automation.selenium.web.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import br.com.safra.automation.selenium.domain.Tempo;

public class ClicaUtil extends UtilidadesDeDriver{

	ElementoUtil elementoUtil = new ElementoUtil();
	EsperaUtil esperaUtil = new EsperaUtil();
	
	/**<p>new Actions( getDriver() ).moveToElement( element )</p>
	 * 
	 * Move o cursor até o centro de um elemento.*/
	public void moveAoElemento( By by )
	{
		moveAoElemento( elementoUtil.buscaElemento( by ) );
	}
	
	/**<p>new Actions( getDriver() ).moveToElement( element )</p>
	 * 
	 * Move o cursor até o centro de um elemento.*/
	public void moveAoElemento( WebElement element )
	{
		new Actions( getDriver() ).moveToElement( element )
			.click().build().perform();
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento, sem esperar que esteja clicável.*/
	public void clicaSimples(By by)
	{
		clicaSimples( elementoUtil.buscaElemento(by) );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento, sem esperar que esteja clicável.*/
	public void clicaSimples(WebElement element)
	{
		element.click();
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	public void clica(By by)
	{
		clica( by, Tempo._60.qtde() );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	public void clica(By by, int segundosAteTimeout)
	{
		WebElement element = esperaUtil.esperaAteEstarClicavel( by, segundosAteTimeout );
		clicaSimples( element );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	public void clica(WebElement element)
	{
		clica( element, Tempo._60.qtde() );
	}
	
	/**<p>element.click()</p>
	 * 
	 * Clica um elemento.*/
	public void clica(WebElement element, int segundosAteTimeout)
	{
		esperaUtil.esperaAteEstarClicavel(element, segundosAteTimeout);
		clicaSimples(element);
	}

}
