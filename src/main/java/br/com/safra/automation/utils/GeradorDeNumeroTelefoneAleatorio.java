package br.com.safra.automation.utils;

import java.util.Random;

public class GeradorDeNumeroTelefoneAleatorio
{
	/**Gera um número pseudo-aleatório entre o limite inferior (inclusivo)
	 * e o limite superior (exclusivo).
	 * 
	 * @param limiteInferior O menor número que pode ser gerado.
	 * @param limiteSuperior O número imediatamente superior ao
	 * maior número que pode ser gerado.
	 * @return Um inteiro aleatório entre os limites.
	 */
	public int geraInteiroAleatorio(int limiteInferior, int limiteSuperior)
	{
		Random rand = new Random();
		// 9-9999-9999
		return limiteInferior + rand.nextInt( limiteSuperior - limiteInferior );
	}
	
	/**Gera um número aleatório de 9-0000-0000 até 9-9999-9999.
	 * 
	 * @return Um inteiro representando um número de celular.
	 */
	public int geraNumeroDeCelularAleatorio()
	{
		return geraInteiroAleatorio( 900000000, 1000000000 );
	}
}
