package br.com.safra.automation.selenium.config.properties;

import java.io.File;
import java.util.Properties;

import br.com.safra.automation.storage.LeitorDeArquivoProperties;

public class ProjectProperties extends GerenciadorDeProperties
{
	protected ProjectProperties() {}
	
	protected static ProjectProperties gerenciador;
	
	protected String pastaDownloads, pastaEvidencias, pastaRelatorios, pastaLog, arquivoUsuarios;
	
	/**./config/project.properties*/
	@Override
	protected void setDefaultFileLocation()
	{
		setCustomFileLocation( "./config/project.properties" );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized ProjectProperties getInstance()
	{
		return getInstance( null );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized ProjectProperties getInstance( String customFileLocation )
	{
		if ( gerenciador == null )
		{
			gerenciador = new ProjectProperties();
			gerenciador.setCustomFileLocation( customFileLocation );
			Properties properties = new LeitorDeArquivoProperties()
					.loadProperties( new File( gerenciador.getFileLocation() ).getAbsolutePath() );
			
			gerenciador.pastaDownloads = properties.getProperty("pasta.downloads");
			gerenciador.pastaEvidencias = properties.getProperty("pasta.evidencias");
			gerenciador.pastaRelatorios = properties.getProperty("pasta.relatorios");
			gerenciador.pastaLog = properties.getProperty("pasta.log");
			gerenciador.arquivoUsuarios = properties.getProperty("arquivo.usuarios");
		}
		return gerenciador;
	}

	public String getPastaDownloads() {
		return pastaDownloads;
	}

	public String getPastaEvidencias() {
		return pastaEvidencias;
	}

	public String getPastaRelatorios() {
		return pastaRelatorios;
	}

	public String getPastaLog() {
		return pastaLog;
	}

	public String getArquivoUsuarios() {
		return arquivoUsuarios;
	}
}
