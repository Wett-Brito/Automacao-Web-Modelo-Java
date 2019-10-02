package br.com.safra.automation.selenium.config.properties;

import java.io.File;
import java.util.Properties;

import br.com.safra.automation.storage.LeitorDeArquivoProperties;

public class SQLConnectionProperties extends GerenciadorDeProperties
{
	protected SQLConnectionProperties() {}
	
	protected static SQLConnectionProperties gerenciador;
	
	protected String sqlServer, database, username, password, codigoProjeto, dataInicio, dataFim;
	protected boolean filtrarEnvioDeStatus;
	
	/**./config/sqlconnection.properties*/
	@Override
	protected void setDefaultFileLocation()
	{
		setCustomFileLocation( "./config/sqlconnection.properties" );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized SQLConnectionProperties getInstance()
	{
		return getInstance( null );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized SQLConnectionProperties getInstance( String customFileLocation )
	{
		if ( gerenciador == null )
		{
			gerenciador = new SQLConnectionProperties();
			gerenciador.setCustomFileLocation( customFileLocation );
			Properties properties = new LeitorDeArquivoProperties()
					.loadProperties( new File( gerenciador.getFileLocation() ).getAbsolutePath() );
			
			gerenciador.sqlServer = properties.getProperty("SqlServer");
			gerenciador.database = properties.getProperty("DataBase");
			gerenciador.username = properties.getProperty("User");
			gerenciador.password = properties.getProperty("Password");
			gerenciador.codigoProjeto = properties.getProperty("CodigoProjeto");
			gerenciador.dataInicio = properties.getProperty("dataInicio");
			gerenciador.dataFim = properties.getProperty("dataFim");
			gerenciador.filtrarEnvioDeStatus = properties.getProperty("filtrarEnvioDeStatus")
					.equals("true") ? true : false;
		}
		return gerenciador;
	}

	public String getSqlServer() {
		return sqlServer;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getCodigoProjeto() {
		return codigoProjeto;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public boolean isFiltrarEnvioDeStatus() {
		return filtrarEnvioDeStatus;
	}
}
