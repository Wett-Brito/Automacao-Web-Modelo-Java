package br.com.safra.automation.selenium.config.testclasses;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;

import br.com.safra.automation.selenium.domain.TestBase;
import br.com.safra.automation.selenium.exceptions.ConfigurationException;
import br.com.safra.automation.storage.GerenciadorDeArquivoListavel;

abstract class GerenciadorDeClassesDeTeste extends GerenciadorDeArquivoListavel<String>
{
	private static final Logger LOG = LogManager.getLogger(GerenciadorDeClassesDeTeste.class);
	
	/**Listagem do conteúdo do arquivo.
	 * @throws ConfigurationException se o conteúdo estiver vazio.*/
	@Override
	public List<String> getList() throws ConfigurationException
	{
		if ( list.isEmpty() )
		{
			String mensagem = String.format("Não foi encontrado"
					+ " nenhum conteúdo em '%s' ...", getFileLocation());
			LOG.error( mensagem );
			throw new ConfigurationException( mensagem );
		}
		return list;
	}
	
	/**Retorna o nome canônico da classe contida em 'jsonElement',
	 * comparando-a com as subclasses de 'TestBase.class' contidas no projeto.
	 * @throws ConfigurationException se a classe não for encontrada
	 * ou se não for compatível com TestBase.class.*/
	protected static String encontrarClasseDeTeste( JsonElement jsonElement ) throws ConfigurationException
	{
		String nomeDaClasse = null;
		try {
			nomeDaClasse = jsonElement.getAsString();
			Class<?> classe = Class.forName( nomeDaClasse, false, TestBase.class.getClassLoader() );
			validarSuperClasse( classe, TestBase.class );
			return classe.getCanonicalName();
		}
		catch ( ClassNotFoundException x )
		{
			String mensagem = String.format("Não foi encontrada a classe de nome '%s' ...", nomeDaClasse);
			LOG.error( mensagem, x );
			throw new ConfigurationException( x );
		}
	}
	
	/**Retorna true se 'classe' é subclasse de 'superclasseCorreta'.
	 * @throws ConfigurationException se os tipos forem incompatíveis.*/
	protected static boolean validarSuperClasse( Class<?> classe, Class<?> superclasseCorreta ) throws ConfigurationException
	{
		Class<?> superclasse = classe.getSuperclass();
		while ( !superclasse.equals( Object.class ) && !superclasse.equals( superclasseCorreta ) )
			superclasse = superclasse.getSuperclass();
		if ( !superclasse.equals( superclasseCorreta ) )
		{
			String mensagem = String.format("A classe de teste '%s' deve ter como"
					+ " superclasse '%s' ou uma de suas subclasses ...",
					classe.getCanonicalName(),
					superclasseCorreta.getCanonicalName());
			LOG.error( mensagem );
			throw new ConfigurationException( mensagem );
		}
		return true;
	}
}
