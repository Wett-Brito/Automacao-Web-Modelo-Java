package br.com.safra.automation.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.safra.automation.selenium.exceptions.AutomationException;

public class LeitorDeArquivoProperties
{
	private static final Logger LOG = LogManager.getLogger(LeitorDeArquivoProperties.class);

	public Properties loadProperties( String fileName )
	{
		Properties props = new Properties();
		try ( InputStream is = new FileInputStream( new File(fileName) ) )
		{
			props.load(is);
			return props;
		}
		catch ( IOException e )
		{
			String mensagem = "Falha ao carregar o arquivo de Properties: " + fileName;
			LOG.error( mensagem, e );
			throw new AutomationException( mensagem, e );
		}
	}
}
