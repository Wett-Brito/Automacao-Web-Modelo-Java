package br.com.safra.automation.selenium.domain;

/**Medidas de tempo em milissegundos (_000) ou em segundos (SEG_000)
 * que podem ser, por exemplo, usadas em funções que requerem tempo de espera.
 *
 */
public enum Tempo
{
	_100 (100),
	_300 (300),
	_500 (500),
	_1 (1),
	_3 (3),
	_5 (5),
	_10 (10),
	_30 (30),
	_60 (60),
	_120 (120);
	
	private final int valor;
	
	private Tempo( final int valor )
	{
		this.valor = valor;
	}
	
	public int qtde()
	{
		return valor;
	}
	
	public int vezesMil()
	{
		return valor * 1_000;
	}
}
