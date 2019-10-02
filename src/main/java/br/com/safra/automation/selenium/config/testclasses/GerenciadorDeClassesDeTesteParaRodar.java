package br.com.safra.automation.selenium.config.testclasses;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import br.com.safra.automation.storage.LeitorDeArquivoJson;

public class GerenciadorDeClassesDeTesteParaRodar extends GerenciadorDeClassesDeTeste
{
	protected GerenciadorDeClassesDeTesteParaRodar() {}
	
	protected static GerenciadorDeClassesDeTesteParaRodar gerenciador;
	
	/**./config/classes_para_rodar.json*/
	@Override
	protected void setDefaultFileLocation()
	{
		setCustomFileLocation( "./config/classes_para_rodar.json" );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized GerenciadorDeClassesDeTesteParaRodar getInstance()
	{
		return getInstance( null );
	}
	
	/**Obtém uma instância contendo uma lista de todos os dispositivos.*/
	public static synchronized GerenciadorDeClassesDeTesteParaRodar getInstance( String customFileLocation )
	{
		if ( gerenciador == null )
		{
			gerenciador = new GerenciadorDeClassesDeTesteParaRodar();
			gerenciador.setCustomFileLocation( customFileLocation );
			JsonObject arquivoInteiro = new LeitorDeArquivoJson()
					.carregar( new File( gerenciador.getFileLocation() ).getAbsolutePath() );
			
			gerenciador.list = new ArrayList<String>();
			arquivoInteiro.getAsJsonArray( "Para rodar" ).forEach(
					e -> gerenciador.list.add(
							encontrarClasseDeTeste( e ) ) );
			validarQueNaoHaDuplicados( gerenciador.getList() );
		}
		return gerenciador;
	}
}
