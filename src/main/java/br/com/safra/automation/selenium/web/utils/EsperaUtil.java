package br.com.safra.automation.selenium.web.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.com.safra.automation.selenium.domain.Tempo;

public class EsperaUtil extends UtilidadesDeDriver{

	ElementoUtil elementoUtil = new ElementoUtil();

	/**<p>Thread.sleep(milliseconds)</p>
	 * 
	 * Põe a Thread presente em estado de espera.*/
	public void espera( int milliseconds )
	{
		try {
			Thread.sleep( milliseconds );
		}
		catch ( InterruptedException x )
		{
			System.err.println( x );
		}
	}
	
	/**Retorna se isDisplayed().*/
	public boolean estaVisivel( By by )
	{
		return estaVisivel( by, Tempo._60.vezesMil() );
	}
	
	/**Retorna se isDisplayed(), em um intervalo de tempo.*/
	public boolean estaVisivel( By by, int miliSeg )
	{
		miliSeg = miliSeg / 1000;
		for ( int i = 1; i <= miliSeg; i++ )
		{
			try {
				if ( elementoUtil.buscaElemento(by).isDisplayed() )
				{
					return true;
				}
			}
			catch ( Exception e )
			{
				espera(1000);
			}
		}
		return false;
	}
	
	/**Retorna se isDisplayed().*/
	public boolean estaVisivel( WebElement element )
	{
		return estaVisivel( element, Tempo._60.vezesMil() );
	}
	
	/**Retorna se isDisplayed(), em um intervalo de tempo.*/
	public boolean estaVisivel( WebElement element, int miliSeg )
	{
		miliSeg = miliSeg / 1000;
		for ( int i = 1; i <= miliSeg; i++ )
		{
			try {
				if ( element.isDisplayed() )
				{
					return true;
				}
			}
			catch ( Exception e )
			{
				espera(1_000);
			}
		}
		return false;
	}
	
	/**Espera até que um elemento esteja ticado/marcado ou retorna nulo.*/
	public boolean esperaAteEstarTicado(By by)
	{
		return esperaAteEstarTicado( by, Tempo._60.qtde() );
	}
	
	/**Espera até que um elemento esteja ticado/marcado ou retorna nulo.*/
	public boolean esperaAteEstarTicado(By by, int segundosAteTimeout)
	{
		return getWait().withTimeout( Duration.ofSeconds( segundosAteTimeout ) )
			.pollingEvery( Duration.ofMillis( Tempo._300.qtde() ) )
			.ignoring(NoSuchElementException.class)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementSelectionStateToBe(by, true));
	}
	
	/**Espera até que um elemento esteja ticado/marcado ou retorna nulo.*/
	public boolean esperaAteEstarTicado(WebElement element)
	{
		return esperaAteEstarTicado( element, Tempo._60.qtde() );
	}
	
	/**Espera até que um elemento esteja ticado/marcado ou retorna nulo.*/
	public boolean esperaAteEstarTicado(WebElement element, int segundosAteTimeout)
	{
		return getWait().withTimeout( Duration.ofSeconds( segundosAteTimeout ) )
			.pollingEvery( Duration.ofMillis( Tempo._300.qtde() ) )
			.ignoring(NoSuchElementException.class)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementSelectionStateToBe(element, true));
	}
	
	/**Espera até que um elemento esteja clicável ou retorna nulo.*/
	public WebElement esperaAteEstarClicavel(By by) {
		return esperaAteEstarClicavel( by, Tempo._60.qtde() );
	}
	
	/**Espera até que um elemento esteja clicável ou retorna nulo.*/
	public WebElement esperaAteEstarClicavel(By by, int segundosAteTimeout)
	{
		return getWait().withTimeout( Duration.ofSeconds( segundosAteTimeout ) )
			.pollingEvery( Duration.ofMillis( Tempo._300.qtde() ) )
			.ignoring(NoSuchElementException.class)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	/**Espera até que um elemento esteja clicável ou retorna nulo.*/
	public WebElement esperaAteEstarClicavel(WebElement element)
	{
		return esperaAteEstarClicavel( element, Tempo._60.qtde() );
	}
	
	/**Espera até que um elemento esteja clicável ou retorna nulo.*/
	public WebElement esperaAteEstarClicavel(WebElement element, int segundosAteTimeout)
	{
		return getWait().withTimeout( Duration.ofSeconds( segundosAteTimeout ) )
			.pollingEvery( Duration.ofMillis( Tempo._300.qtde() ) )
			.ignoring(StaleElementReferenceException.class)
			.ignoring(NoSuchElementException.class)
			.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/**Espera até que um elemento esteja visível ou retorna nulo.*/
	public WebElement esperaVisibilidadeDe(By by)
	{
		return esperaVisibilidadeDe( by, Tempo._60.qtde() );
	}
	
	/**Espera até que um elemento esteja visível ou retorna nulo.*/
	public WebElement esperaVisibilidadeDe(By by, int segundosAteTimeout)
	{
		return getWait().withTimeout( Duration.ofSeconds( segundosAteTimeout ) )
			.pollingEvery( Duration.ofMillis( Tempo._300.qtde() ) )
			.ignoring(StaleElementReferenceException.class)
			.ignoring(NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**Espera até que um elemento esteja visível ou retorna nulo.*/
	public WebElement esperaVisibilidadeDe(WebElement element)
	{
		return esperaVisibilidadeDe( element, Tempo._60.qtde() );
	}
	
	/**Espera até que um elemento esteja visível ou retorna nulo.*/
	public WebElement esperaVisibilidadeDe(WebElement element, int segundosAteTimeout)
	{
		return getWait().withTimeout( Duration.ofSeconds( segundosAteTimeout ) )
			.pollingEvery( Duration.ofMillis( Tempo._300.qtde() ) )
			.ignoring(StaleElementReferenceException.class)
			.ignoring(NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**Espera até que o elemento:
	 * <p>(1) não seja encontrado; ou</p>
	 * <p>(2) esteja com referência caduca; ou</p>
	 * <p>(3) não seja exibido em tela; ou</p>
	 * <p>(4) tenha largura e altura ambas iguais a zero; ou</p>
	 * <p>(5) o tempo limite de espera seja atingido</p>
	 * 
	 * @param by localizador do elemento
	 * @return true se o elemento não for encontrado ou se a referência ao elemento
	 *         caducar*/
	public boolean esperaAteElementoSumir(By by)
	{
		return esperaAteElementoSumir( by, Tempo._60.qtde() );
	}
	
	/**Espera até que o elemento:
	 * <p>(1) não seja encontrado; ou</p>
	 * <p>(2) esteja com referência caduca; ou</p>
	 * <p>(3) não seja exibido em tela; ou</p>
	 * <p>(4) tenha largura e altura ambas iguais a zero; ou</p>
	 * <p>(5) o tempo limite de espera seja atingido</p>
	 * 
	 * @param by localizador do elemento
	 * @param segundosAteTimeout tempo limite de espera
	 * @return true se o elemento não for encontrado ou se a referência ao elemento
	 *         caducar*/
	public boolean esperaAteElementoSumir(By by, int segundosAteTimeout)
	{
		WebElement element = null;
		boolean sumiu = false;
		try {
			element = elementoUtil.buscaElemento(by);
			sumiu = esperaAteElementoSumir(element, segundosAteTimeout);
		}
		catch (NoSuchElementException | StaleElementReferenceException e)
		{
			sumiu = true;
		}
		return sumiu;
	}
	
	/**Espera até que o elemento:
	 * <p>(1) não seja encontrado; ou</p>
	 * <p>(2) esteja com referência caduca; ou</p>
	 * <p>(3) não seja exibido em tela; ou</p>
	 * <p>(4) tenha largura e altura ambas iguais a zero; ou</p>
	 * <p>(5) o tempo limite de espera seja atingido</p>
	 * 
	 * @param element o elemento
	 * @return true se o elemento não for encontrado ou se a referência ao elemento
	 *         caducar*/
	public boolean esperaAteElementoSumir(WebElement element)
	{
		return esperaAteElementoSumir( element, Tempo._60.qtde() );
	}
	
	/**Espera até que o elemento:
	 * <p>(1) não seja encontrado; ou</p>
	 * <p>(2) esteja com referência caduca; ou</p>
	 * <p>(3) não seja exibido em tela; ou</p>
	 * <p>(4) tenha largura e altura ambas iguais a zero; ou</p>
	 * <p>(5) o tempo limite de espera seja atingido</p>
	 * 
	 * @param element o elemento
	 * @param segundosAteTimeout tempo limite de espera
	 * @return true se o elemento não for encontrado ou se a referência ao elemento
	 *         caducar*/
	public boolean esperaAteElementoSumir(WebElement element, int segundosAteTimeout)
	{
		boolean sumiu = false;
		int contadorSegundos = 0;
		try {
			while (contadorSegundos < segundosAteTimeout && !sumiu)
			{
				if (!element.isDisplayed()
						|| (element.getSize().getWidth() == 0
							&& element.getSize().getHeight() == 0))
				{
					sumiu = true;
				}
				espera(1_000);
				contadorSegundos++;
			}
		}
		catch (NoSuchElementException | StaleElementReferenceException e)
		{
			sumiu = true;
		}
		return sumiu;
	}

	
}
