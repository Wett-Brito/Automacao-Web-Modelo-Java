package br.com.safra.automation.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.safra.automation.selenium.exceptions.ConfigurationException;

abstract public class GerenciadorDeArquivoListavel<E> extends GerenciadorDeArquivo
{
	private static final Logger LOG = LogManager.getLogger(GerenciadorDeArquivoListavel.class);
	
	protected List<E> list;
	
	/**Listagem do conteúdo do arquivo.*/
	public List<E> getList()
	{
		return list;
	}
	
	/**Retorna true se não houver nenhum elemento duplicado.
	 * @throws ConfigurationException se houver elementos duplicados.*/
	protected static <T> boolean validarQueNaoHaDuplicados( List<T> listaParaAnalisar ) throws ConfigurationException
	{
		final Set<T> duplicados = acharDuplicados( listaParaAnalisar );
		if ( !duplicados.isEmpty() )
		{
			String mensagem = "É necessário remover os seguintes elementos duplicados:";
			for ( T d : duplicados )
			{
				mensagem = mensagem.concat( "\n'" + d.toString() + "'" );
			}
			LOG.error( mensagem );
			throw new ConfigurationException( mensagem );
		}
		return true;
	}
	
	/**Encontra os elementos duplicados de uma lista, usando equals().*/
	protected static <T> Set<T> acharDuplicados( List<T> listaParaAnalisar )
	{
		final Set<T> unicos = new HashSet<>();
		final Set<T> duplicados = new HashSet<>();
		listaParaAnalisar.forEach( e -> { if ( !unicos.add( e ) ) duplicados.add( e ); } );
		return duplicados;
	}
}
