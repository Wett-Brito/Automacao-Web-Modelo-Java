package br.com.safra.automation.selenium.config.properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoopProperties extends GerenciadorDeProperties
{
	private static final Logger LOG = LogManager.getLogger(LoopProperties.class);
	
	protected boolean loop;
	
	/**./config/loop.properties*/
	@Override
	protected void setDefaultFileLocation()
	{
		setCustomFileLocation( "./config/loop.properties" );
	}
	
	public LoopProperties()
	{
		this( null );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public LoopProperties( String customFileLocation )
	{
		setCustomFileLocation( customFileLocation );
		try ( BufferedReader oB = new BufferedReader( new FileReader( getFileLocation() ) ) )
		{
			while ( oB.ready() )
			{
				loop = oB.readLine().replace("loop=", "").trim().equals("true");
			}
		}
		catch (IOException e)
		{
			String mensagem = "Falha ao carregar o arquivo de Properties: " + getFileLocation();
			LOG.error( mensagem, e );
		}
	}

	public boolean isLoop()
	{
		return loop;
	}
	
}
